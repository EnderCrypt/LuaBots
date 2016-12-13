package com.github.endercrypt.gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tileset
{
	public static final int TILE_SIZE = 32;

	private Image[] tiles;

	public Tileset(File file) throws IOException
	{
		BufferedImage image = ImageIO.read(file);
		final int width = image.getWidth();
		final int height = image.getHeight();

		if ((width % TILE_SIZE != 0) || (height % TILE_SIZE != 0))
			throw new RuntimeException("tileset image needs to be divadable by " + TILE_SIZE);

		final int tWidth = (width / TILE_SIZE);
		final int tHeight = (height / TILE_SIZE);

		tiles = new Image[tWidth * tHeight];

		for (int y = 0; y < tHeight; y++)
		{
			for (int x = 0; x < tWidth; x++)
			{
				tiles[x + (y * tHeight)] = image.getSubimage(x * 32, y * 32, 32, 32);
			}
		}
	}

	public int getTileCount()
	{
		return tiles.length;
	}

	public Image get(int index)
	{
		return tiles[index];
	}
}
