package com.github.endercrypt.gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.endercrypt.game.Orientation;
import com.github.endercrypt.game.map.IntPosition;

public enum Graphics
{
	BOT("bot");

	private final static String baseResourceDir = "resources/";
	private final static String extension = "png";

	private File file;
	private BufferedImage image;
	private int width;
	private int height;

	private Graphics(String filename)
	{
		file = new File(baseResourceDir + filename + "." + extension);
		try
		{
			image = ImageIO.read(file);
			width = image.getWidth();
			height = image.getHeight();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private Image getImage()
	{
		return image;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public int getCenterWidth()
	{
		return width / 2;
	}

	public int getCenterHeight()
	{
		return height / 2;
	}

	/**
	 * @param botPosition  
	 */
	public void draw(Graphics2D g2d, IntPosition botPosition, Orientation orientation)
	{
		AffineTransform transformation = new AffineTransform();
		transformation.translate(Math.floor(botPosition.getPixelX()) - getCenterWidth(), Math.floor(botPosition.getPixelY()) - getCenterHeight());
		transformation.rotate((Math.PI / 2) - orientation.getRadians(), image.getWidth() / 2, image.getHeight() / 2);
		g2d.drawImage(getImage(), transformation, null);
	}
}
