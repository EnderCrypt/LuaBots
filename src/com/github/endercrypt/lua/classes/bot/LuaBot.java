package com.github.endercrypt.lua.classes.bot;

import java.awt.Graphics2D;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaNumber;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import com.endercrypt.luaj.extended.clazz.LuaClassFactory;
import com.endercrypt.luaj.extended.clazz.LuaClassFactory.LuaClass;
import com.endercrypt.luaj.extended.clazz.LuaMethod;
import com.github.endercrypt.game.LuaHooks;
import com.github.endercrypt.game.Orientation;
import com.github.endercrypt.game.map.IntPosition;
import com.github.endercrypt.game.map.GameEntity;
import com.github.endercrypt.game.map.GameMap;
import com.github.endercrypt.game.map.Tile;
import com.github.endercrypt.gui.Graphics;

public class LuaBot extends GameEntity
{
	private static LuaClass luaClass = LuaClassFactory.scan(LuaBot.class);

	private LuaTable lua;
	private Graphics graphics = Graphics.BOT;

	private boolean hasAction = false;
	private BotData botData;

	public LuaBot()
	{
		lua = luaClass.obtain(this);
		botData = new BotData(this, lua);
	}

	public void born(LuaHooks luaHooks)
	{
		botData.luaInjectData();
		luaHooks.onBorn(this);
	}

	public LuaTable getLua()
	{
		return lua;
	}

	public BotData getBotData()
	{
		return botData;
	}

	@Override
	public void update(LuaHooks luaHooks, int turn)
	{
		botData.update();
		botData.luaInjectData();
		hasAction = true;
		luaHooks.onBotUpdate(this, turn);
		hasAction = false;
	}

	@LuaMethod("forward")
	public LuaValue LUA_ActionForward()
	{
		if (hasAction == false)
			return LuaValue.NIL;
		hasAction = false;
		IntPosition position = getPosition().getPositionInDirection(botData.getOrientation());
		moveTo(position);
		return LuaValue.TRUE;
	}

	@LuaMethod("backward")
	public LuaValue LUA_ActionBackward()
	{
		if (hasAction == false)
			return LuaValue.NIL;
		hasAction = false;
		IntPosition position = getPosition().getPositionInDirection(botData.getOrientation().getReversed());
		moveTo(position);
		return LuaValue.TRUE;
	}

	@LuaMethod("strafeLeft")
	public LuaValue LUA_ActionStrafeLeft()
	{
		if (hasAction == false)
			return LuaValue.NIL;
		hasAction = false;
		IntPosition position = getPosition().getPositionInDirection(botData.getOrientation().getLeft());
		moveTo(position);
		return LuaValue.TRUE;
	}

	@LuaMethod("strafeRight")
	public LuaValue LUA_ActionStrafeRight()
	{
		if (hasAction == false)
			return LuaValue.NIL;
		hasAction = false;
		IntPosition position = getPosition().getPositionInDirection(botData.getOrientation().getRight());
		moveTo(position);
		return LuaValue.TRUE;
	}

	@LuaMethod("rotateLeft")
	public LuaValue LUA_ActionRotateLeft()
	{
		if (hasAction == false)
			return LuaValue.NIL;
		hasAction = false;
		botData.setOrientation(botData.getOrientation().getLeft());
		return LuaValue.TRUE;
	}

	@LuaMethod("rotateRight")
	public LuaValue LUA_ActionRotateRight()
	{
		if (hasAction == false)
			return LuaValue.NIL;
		hasAction = false;
		botData.setOrientation(botData.getOrientation().getRight());
		return LuaValue.TRUE;
	}

	@LuaMethod("rotate")
	public LuaValue LUA_ActionRotate(LuaNumber angle)
	{
		if (hasAction == false)
			return LuaValue.NIL;
		Orientation newOrientation = Orientation.getOrientationByAngle(angle.checkdouble());
		if (botData.getOrientation() == newOrientation)
		{
			return LuaBoolean.FALSE;
		}
		else
		{
			botData.setOrientation(newOrientation);
			hasAction = false;
			return LuaBoolean.TRUE;
		}
	}

	@LuaMethod("reproduce")
	public LuaValue LUA_ActionReproduce()
	{
		/*if (hasAction == false)
			return LuaValue.NIL;
		EntityPosition position = getPosition();
		EntityPosition behind = position.getPositionInDirection(botData.getOrientation().getReversed());
		if (behind.get)*/
		return LuaBoolean.FALSE;
	}

	@LuaMethod("hasAction")
	public LuaValue LUA_HasAction()
	{
		return LuaBoolean.valueOf(hasAction);
	}

	@Override
	public boolean isAlive()
	{
		return true;//(botData.getHealth() > 0);
	}

	@Override
	public void draw(Graphics2D g2d)
	{
		graphics.draw(g2d, getPosition(), botData.getOrientation());
	}
}
