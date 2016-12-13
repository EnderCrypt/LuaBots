package com.github.endercrypt.game.map;

import java.awt.Graphics2D;

import com.github.endercrypt.game.LuaHooks;

public abstract class GameEntity
{
	protected int ID;
	protected Tile tile = null;

	public int getID()
	{
		return ID;
	}

	public boolean moveTo(IntPosition position)
	{
		return moveTo(position.getX(), position.getY());
	}

	public boolean moveTo(int x, int y)
	{
		return tile.moveEntityTo(x, y);
	}

	public GameMap getMap()
	{
		return tile.getMap();
	}

	public Tile getTile()
	{
		return tile;
	}

	public IntPosition getPosition()
	{
		return tile.getPosition();
	}

	public abstract boolean isAlive();

	public abstract void update(LuaHooks luaHooks, int turn);

	public abstract void draw(Graphics2D g2d);
}
