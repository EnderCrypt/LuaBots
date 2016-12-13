package com.github.endercrypt.game.map;

import com.github.endercrypt.game.Orientation;

public class IntPosition
{
	private int x;
	private int y;

	public IntPosition()
	{
		this(0, 0);
	}

	public IntPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getPixelX()
	{
		return (x * 32) + 16;
	}

	public int getPixelY()
	{
		return (y * 32) + 16;
	}

	public IntPosition getPositionInDirection(Orientation orientation)
	{
		int nx = x;
		int ny = y;
		switch (orientation)
		{
		case NORTH:
			ny -= 1;
			break;
		case WEST:
			nx -= 1;
			break;
		case SOUTH:
			ny += 1;
			break;
		case EAST:
			nx += 1;
			break;
		}
		return new IntPosition(nx, ny);
	}
}
