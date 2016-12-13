package com.github.endercrypt.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.endercrypt.game.ScreenDrawer;
import com.github.endercrypt.gui.keyboard.Keyboard;

public class Screen
{
	private JFrame jFrame;
	private ScreenPanel screenPanel;
	private ScreenDrawer screenDrawer;
	private Keyboard keyboard;
	private CountDownLatch screenDrawWait = new CountDownLatch(1);

	public Screen()
	{
		screenPanel = new ScreenPanel();
		//screenPanel.setBackground(new Color(200, 255, 255));
		screenPanel.setPreferredSize(new Dimension(1000, 500));

		jFrame = new JFrame();
		jFrame.add(screenPanel);
		jFrame.pack();
		jFrame.setLocationRelativeTo(null);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);

		keyboard = new Keyboard(jFrame);
	}

	public void waitForScreen() throws InterruptedException
	{
		screenDrawWait.await();
	}

	@SuppressWarnings("serial")
	private class ScreenPanel extends JPanel
	{
		@Override
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Graphics2D g2dHud = (Graphics2D) g.create();
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.drawString("", 0, 0);
			screenDrawWait.countDown();
			//g2d.drawString(getScreenSize().toString(), 10, 20);
			if (screenDrawer != null)
			{
				// Game
				screenDrawer.draw(g2d);

				// HUD
				screenDrawer.drawHud(g2dHud);
			}
		}
	}

	public Dimension getScreenSize()
	{
		return screenPanel.getSize();
	}

	public Keyboard getKeyboard()
	{
		return keyboard;
	}

	public void setDrawer(ScreenDrawer screenDrawer)
	{
		this.screenDrawer = screenDrawer;
	}

	public void repaint()
	{
		screenPanel.repaint();
	}
}
