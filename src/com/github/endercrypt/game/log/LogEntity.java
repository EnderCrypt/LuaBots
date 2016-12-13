package com.github.endercrypt.game.log;

import java.awt.Graphics2D;

public interface LogEntity
{
	public void update();

	public boolean isExpired();

	public float getHeight();

	public void draw(Graphics2D g2d, float x, float y);

	public LogEntity getReplacer();
}
