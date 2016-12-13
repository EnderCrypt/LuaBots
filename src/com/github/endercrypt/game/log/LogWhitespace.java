package com.github.endercrypt.game.log;

import java.awt.Graphics2D;

public class LogWhitespace implements LogEntity
{
	private float height;
	private double heightRemove = 0.0;

	public LogWhitespace(float height)
	{
		this.height = height;
	}

	@Override
	public void update()
	{
		height -= heightRemove;
		heightRemove += 0.01;
	}

	@Override
	public boolean isExpired()
	{
		return (height < 0.5);
	}

	@Override
	public float getHeight()
	{
		return height;
	}

	@Override
	public void draw(Graphics2D g2d, float x, float y)
	{
		// do nothing
	}

	@Override
	public LogEntity getReplacer()
	{
		return null;
	}

}
