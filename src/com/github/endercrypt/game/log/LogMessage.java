package com.github.endercrypt.game.log;

import java.awt.Color;
import java.awt.Graphics2D;

public class LogMessage implements LogEntity
{
	private String message;
	private Color color;
	private double alpha;
	private double fadeInc = 0.0f;
	private double fadeSpeed = 1.0;

	public LogMessage(String message, Color color, double fadeSpeed)
	{
		this.message = message;
		this.color = color;
		this.alpha = 1.0F;
		this.fadeSpeed = fadeSpeed;
	}

	@Override
	public void update()
	{
		alpha -= fadeInc;
		fadeInc += (0.000025 * fadeSpeed);
	}

	@Override
	public boolean isExpired()
	{
		return (alpha <= 0.0);
	}

	@Override
	public float getHeight()
	{
		return 16f;
	}

	@Override
	public void draw(Graphics2D g2d, float x, float y)
	{
		Color alphaColor = new Color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, (float) alpha);
		g2d.setColor(alphaColor);
		g2d.drawString(message, x, y);
	}

	@Override
	public LogEntity getReplacer()
	{
		return new LogWhitespace(getHeight());
	}
}
