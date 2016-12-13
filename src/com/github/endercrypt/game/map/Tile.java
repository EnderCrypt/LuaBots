package com.github.endercrypt.game.map;

import java.awt.Graphics2D;
import java.awt.Image;

import com.github.endercrypt.gui.Tileset;
import com.github.endercrypt.lua.classes.bot.LuaBot;

public class Tile
{
	private GameMap map;
	private int tx;
	private int ty;
	private int frame;
	private GameEntity entity;

	public Tile(GameMap map, int tx, int ty, int frame)
	{
		this.map = map;
		this.tx = tx;
		this.ty = ty;
		this.frame = frame;
	}

	public IntPosition getPosition()
	{
		return new IntPosition(tx, ty);
	}

	public void setFrame(int frame)
	{
		this.frame = frame;
	}

	public void draw(Graphics2D g2d, Tileset tileset, int x, int y)
	{
		Image image = tileset.get(frame);
		g2d.drawImage(image, x, y, null);
	}

	public GameMap getMap()
	{
		return map;
	}

	protected void removeEntity(GameEntity entity)
	{
		entity.tile = null;
		this.entity = null;
	}

	protected void insertEntity(GameEntity entity)
	{
		if (hasEntity())
			throw new RuntimeException("Game entity already exists");
		this.entity = entity;
		entity.tile = this;
	}

	public GameEntity getEntity()
	{
		return entity;
	}

	public boolean hasEntity()
	{
		return (entity != null);
	}

	public boolean moveEntityTo(int x, int y)
	{
		Tile newTile = map.getTile(x, y);
		if (newTile.hasEntity())
			return false;
		newTile.entity = entity;
		entity.tile = newTile;
		entity = null;
		return true;
	}
}
