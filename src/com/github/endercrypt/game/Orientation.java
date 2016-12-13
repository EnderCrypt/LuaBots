package com.github.endercrypt.game;

public enum Orientation
{
	EAST(0),
	NORTH(90),
	WEST(180),
	SOUTH(270);

	private double angle;
	private double radians;

	private Orientation(double angle)
	{
		this.angle = angle;
		radians = Math.toRadians(angle);
	}

	public double getAngle()
	{
		return angle;
	}

	public double getRadians()
	{
		return radians;
	}

	private static final Orientation[] values = values();

	private static Orientation safeGetOrientation(int index)
	{
		/*
		index = (index % values.length);
		if (index < 0)
			index = values.length + index;
			*/
		index = Math.floorMod(index, values.length);
		return values[index];
	}

	public Orientation getLeft()
	{
		return safeGetOrientation(ordinal() + 1);
	}

	public Orientation getRight()
	{
		return safeGetOrientation(ordinal() - 1);
	}

	public Orientation getReversed()
	{
		return safeGetOrientation(ordinal() + 2);
	}

	public static Orientation random()
	{
		int index = (int) Math.floor(Math.random() * values.length);
		return values[index];
	}

	public static Orientation getOrientationByAngle(double angle)
	{
		angle = angle % 360;
		Orientation closest = null;
		double closestDist = 360;
		for (Orientation orientation : values)
		{
			double dist = Math.abs(orientation.getAngle() - angle);
			if (dist < closestDist)
			{
				closest = orientation;
				closestDist = dist;
			}
		}
		return closest;
	}
}
