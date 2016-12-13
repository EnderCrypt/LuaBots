package com.github.endercrypt.game;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.lib.jse.JsePlatform;

import com.github.endercrypt.game.log.LuaPrintReader;
import com.github.endercrypt.game.map.GameMap;
import com.github.endercrypt.gui.Camera;
import com.github.endercrypt.gui.Screen;
import com.github.endercrypt.gui.Tileset;
import com.github.endercrypt.gui.keyboard.AppKeyListener;
import com.github.endercrypt.gui.keyboard.Keyboard;
import com.github.endercrypt.gui.keyboard.Keyboard.BindType;
import com.github.endercrypt.lua.classes.bot.BotData;
import com.github.endercrypt.lua.classes.bot.LuaBot;
import com.github.endercrypt.lua.library.MathExtended;

public class Game implements ScreenDrawer, AppKeyListener
{
	private static final int framesTurn = 50;
	private Screen screen;
	private Globals globals;
	private Camera camera;
	private Keyboard keyboard;
	private Tileset tileset;
	private GameMap gameMap;
	private LuaHooks luaHooks;
	private LuaPrintReader luaPrintReader = new LuaPrintReader();

	public Game(Screen screen) throws IOException
	{
		this.screen = screen;
		camera = new Camera();
		keyboard = screen.getKeyboard();
		camera.keyboardCallback(keyboard);
		tileset = new Tileset(new File("resources/cs2dnorm.png"));
		gameMap = new GameMap(tileset, 25, 25);
		screen.setDrawer(this);
		globals = JsePlatform.standardGlobals();
		globals.load(new MathExtended());
		luaHooks = new LuaHooks(globals);
		luaPrintReader.install(globals);
		try
		{
			globals.loadfile("Main.lua").call();
		}
		catch (LuaError e)
		{
			luaPrintReader.err.println("LUA Error: " + e.getMessage());
			luaPrintReader.readBuffer();
		}
		gameMap.addBot(luaHooks, 10, 10);
	}

	@Override
	public void keyTriggered(int keycode, BindType bindType)
	{

	}

	public void update(int frame)
	{
		camera.update();
		luaPrintReader.update();

		if (frame % framesTurn == 0)
		{
			int turn = (frame / framesTurn) + 1;
			luaPrintReader.readBuffer();

			luaPrintReader.out.println(" --{ " + turn + " }--");
			luaPrintReader.readBuffer();

			gameMap.turnUpdate(turn, luaHooks, luaPrintReader);
		}
	}

	@Override
	public void draw(Graphics2D g2d)
	{
		gameMap.drawTiles(g2d, camera.getPosition(), screen.getScreenSize());
		camera.applyCamera(g2d);
		gameMap.drawOutline(g2d);
		gameMap.drawEntities(g2d);
	}

	@Override
	public void drawHud(Graphics2D g2d)
	{
		luaPrintReader.draw(screen, g2d);
	}
}
