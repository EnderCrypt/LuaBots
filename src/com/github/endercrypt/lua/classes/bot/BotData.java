package com.github.endercrypt.lua.classes.bot;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import com.github.endercrypt.game.Orientation;
import com.github.endercrypt.game.map.IntPosition;

public class BotData
{
	private LuaBot luaBot;
	private LuaTable luaBotTable;

	private Orientation orientation;

	private int age = 0;
	private double health = 100;
	private double maxHealth = 100;
	private double fullness = 100;
	private double maxFullness = 100;
	private double metabolism = 2.5;

	public BotData(LuaBot luaBot, LuaTable luaBotTable)
	{
		this.luaBot = luaBot;
		this.luaBotTable = luaBotTable;
		orientation = Orientation.random();
	}

	public void luaInjectData()
	{
		luaBotTable.set("id", luaBot.getID());
		luaBotTable.set("name", "untitled");
		luaBotTable.set("age", age);
		IntPosition position = luaBot.getPosition();
		luaBotTable.set("x", position.getX());
		luaBotTable.set("y", position.getY());
		luaBotTable.set("orientation", orientation.toString());
		luaBotTable.set("health", health);
		luaBotTable.set("maxHealth", maxHealth);
		luaBotTable.set("fullness", fullness);
		luaBotTable.set("maxFullness", maxFullness);
		luaBotTable.set("metabolism", metabolism);
	}

	public void update()
	{
		age++;
		addHealth(fullness / 20.0);
		addFullness(-metabolism);
	}

	private static double maxMin(double value, double maxmin)
	{
		if (value > maxmin)
			value = maxmin;
		if (value < -maxmin)
			value = -maxmin;
		return value;
	}

	public void addFullness(double value)
	{
		fullness += maxMin(value, maxFullness);
	}

	public void addHealth(double value)
	{
		health += maxMin(value, maxHealth);
	}

	// SETTERS //

	public void setOrientation(Orientation orientation)
	{
		this.orientation = orientation;
	}

	// GETTERS //

	public double getHealth()
	{
		return health;
	}

	public Orientation getOrientation()
	{
		return orientation;
	}

	public String getName()
	{
		LuaValue luaValue = luaBotTable.get("name");
		if (luaValue.isstring())
			return luaValue.checkjstring();
		return null;
	}
}
