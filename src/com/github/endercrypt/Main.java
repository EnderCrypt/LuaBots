package com.github.endercrypt;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.github.endercrypt.game.Game;
import com.github.endercrypt.gui.Screen;
import com.github.endercrypt.gui.keyboard.Keyboard;

public class Main
{
	private static final int fps = 50;
	private static int timeCount = 0;

	private static Screen screen;
	private static Keyboard keyboard;
	private static Game game;
	private static Timer timer = new Timer();

	public static void main(String[] args) throws InterruptedException, IOException
	{
		screen = new Screen();
		screen.waitForScreen();
		keyboard = screen.getKeyboard();
		game = new Game(screen);
		startGameUpdate();
	}

	private static void startGameUpdate()
	{
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				keyboard.update();
				game.update(timeCount);
				timeCount++;
				screen.repaint();
			}
		}, (1000 / fps), (1000 / fps));
	}
}
