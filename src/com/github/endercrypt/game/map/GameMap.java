package com.github.endercrypt.game.map;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.luaj.vm2.LuaError;

import com.endercrypt.library.position.Position;
import com.github.endercrypt.game.LuaHooks;
import com.github.endercrypt.game.log.LuaPrintReader;
import com.github.endercrypt.gui.Tileset;
import com.github.endercrypt.lua.classes.bot.BotData;
import com.github.endercrypt.lua.classes.bot.LuaBot;

public class GameMap
{
	private Tileset tileset;
	private int mapWidth;
	private int mapHeight;

	private Tile[][] tiles;

	private int idCounter = 1;
	private Map<Integer, GameEntity> entities = new HashMap<>();

	public GameMap(Tileset tileset, int mapWidth, int mapHeight)
	{
		this.tileset = tileset;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		tiles = new Tile[mapWidth][mapHeight];
		for (int y = 0; y < mapHeight; y++)
		{
			for (int x = 0; x < mapWidth; x++)
			{
				tiles[x][y] = new Tile(this, x, y, 1);
			}
		}
		final int edgeTile = 41;
		for (int x = 0; x < mapWidth; x++)
		{
			tiles[x][0].setFrame(edgeTile);
			tiles[x][mapWidth - 1].setFrame(edgeTile);
		}
		for (int y = 0; y < mapHeight; y++)
		{
			tiles[0][y].setFrame(edgeTile);
			tiles[mapWidth - 1][y].setFrame(edgeTile);
		}
	}

	public void addBot(LuaHooks luaHooks, int x, int y)
	{
		Tile tile = getTile(x, y);
		LuaBot bot = new LuaBot();
		bot.ID = idCounter;
		tile.insertEntity(bot);
		entities.put(idCounter, bot);
		idCounter++;

		bot.born(luaHooks);
	}

	public void remove(GameEntity entity)
	{
		entity.tile.removeEntity(entity);
		entities.remove(entity.ID);
	}

	public void turnUpdate(int turn, LuaHooks luaHooks, LuaPrintReader luaPrintReader)
	{
		try
		{
			luaHooks.onNewTurn(turn);
			luaPrintReader.readBuffer();
			for (GameEntity entity : entities.values())
			{
				if (entity.isAlive())
				{
					entity.update(luaHooks, turn);
					luaPrintReader.readBuffer();
				}
				else
				{
					remove(entity);
				}
			}
		}
		catch (LuaError e)
		{
			luaPrintReader.err.println("LUA Error: " + e.getMessage());
			luaPrintReader.readBuffer();
		}
	}

	public Tile getTile(int x, int y)
	{
		return tiles[x][y];
	}

	public int getWidth()
	{
		return mapWidth;
	}

	public int getHeight()
	{
		return mapHeight;
	}

	public void drawTiles(Graphics2D g2d, Position cameraPosition, Dimension screenSize)
	{
		// the amount of tiles in x/y direction on screen to draw
		int xTiles = (int) ((screenSize.getWidth() / Tileset.TILE_SIZE) + 2);
		int yTiles = (int) ((screenSize.getHeight() / Tileset.TILE_SIZE) + 2);

		// which tile on tiles[x][y] the x/y loop starts at
		int xPosition = (int) Math.floor(cameraPosition.x / 32);
		int yPosition = (int) Math.floor(cameraPosition.y / 32);

		// the exact x/y pixel position where tiles should be drawn onto the screen
		int xStart = (int) Math.floor(xPosition * 32 - cameraPosition.x);
		int yStart = (int) Math.floor(yPosition * 32 - cameraPosition.y);

		for (int y = 0; y < yTiles; y++)
		{
			for (int x = 0; x < xTiles; x++)
			{
				int rx = xPosition + x;
				int ry = yPosition + y;
				if ((rx < 0) || (ry < 0) || (rx >= mapWidth) || (ry >= mapHeight))
					continue;
				int px = xStart + (x * 32);
				int py = yStart + (y * 32);
				tiles[rx][ry].draw(g2d, tileset, px, py);
			}
		}
	}

	public void drawOutline(Graphics2D g2d)
	{
		// draw outline
		g2d.drawRect(0, 0, mapWidth * 32, mapHeight * 32);

		final int outline = 2;
		g2d.drawRect(-outline, -outline, mapWidth * 32 + (outline * 2), mapHeight * 32 + (outline * 2));
	}

	public void drawEntities(Graphics2D g2d)
	{
		for (GameEntity entity : entities.values())
		{
			entity.draw(g2d);
		}
	}
}
