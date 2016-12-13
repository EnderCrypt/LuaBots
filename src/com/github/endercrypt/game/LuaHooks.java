package com.github.endercrypt.game;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaNumber;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import com.github.endercrypt.lua.classes.bot.LuaBot;

public class LuaHooks
{
	private Globals globals;

	public LuaHooks(Globals globals)
	{
		this.globals = globals;
	}

	@SuppressWarnings("unused")
	@Deprecated
	private LuaFunction get(String functionName)
	{
		return globals.get(functionName).checkfunction();
	}

	private void call(String functionName, LuaValue... args)
	{
		LuaValue value = globals.get(functionName);
		if (value.isfunction())
		{
			value.checkfunction().invoke(args);
		}
	}

	public void onBorn(LuaBot luaBot)
	{
		call("onBorn", luaBot.getLua());
	}

	public void onNewTurn(int turn)
	{
		call("onNewTurn", LuaNumber.valueOf(turn));
	}

	public void onBotUpdate(LuaBot luaBot, int turn)
	{
		call("onBotUpdate", luaBot.getLua(), LuaNumber.valueOf(turn));
	}
}
