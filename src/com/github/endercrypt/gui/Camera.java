package com.github.endercrypt.gui;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.endercrypt.library.position.Motion;
import com.endercrypt.library.position.Position;
import com.github.endercrypt.gui.keyboard.AppKeyListener;
import com.github.endercrypt.gui.keyboard.Keyboard;
import com.github.endercrypt.gui.keyboard.Keyboard.BindType;

public class Camera
{
	private Position position;
	private Motion motion;

	public Camera()
	{
		position = new Position(0, 0);
		motion = new Motion();
	}

	public void update()
	{
		position.add(motion);
		motion.multiplyLength(0.75);
	}

	public void keyboardCallback(Keyboard keyboard)
	{
		keyboard.bindKey(KeyEvent.VK_D, Keyboard.BindType.HOLD, new CameraKeyListener());
		keyboard.bindKey(KeyEvent.VK_W, Keyboard.BindType.HOLD, new CameraKeyListener());
		keyboard.bindKey(KeyEvent.VK_A, Keyboard.BindType.HOLD, new CameraKeyListener());
		keyboard.bindKey(KeyEvent.VK_S, Keyboard.BindType.HOLD, new CameraKeyListener());
	}

	public void applyCamera(Graphics2D g2d)
	{
		g2d.translate(Math.floor(-position.x), Math.floor(-position.y));
	}

	public Position getPosition()
	{
		return position;
	}

	private class CameraKeyListener implements AppKeyListener
	{
		@Override
		public void keyTriggered(int keycode, BindType bindType)
		{
			double direction = 0.0;
			switch (keycode)
			{
			case KeyEvent.VK_D:
				direction = 0.0;
				break;
			case KeyEvent.VK_W:
				direction = 270.0;
				break;
			case KeyEvent.VK_A:
				direction = 180.0;
				break;
			case KeyEvent.VK_S:
				direction = 90.0;
				break;
			default:
				return;
			}
			motion.addMotion(Math.toRadians(direction), 2.5);
		}
	}
}
