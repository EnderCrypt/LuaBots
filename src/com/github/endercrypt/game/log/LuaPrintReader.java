package com.github.endercrypt.game.log;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.luaj.vm2.Globals;

import com.github.endercrypt.gui.Screen;

public class LuaPrintReader
{
	private List<LogEntity> logContainer = new LinkedList<>();

	private ByteArrayOutputStream errOutputStream = new ByteArrayOutputStream();
	public final PrintStream err;

	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	public final PrintStream out;

	private final float leftOffset = 5f;
	private final float bottomOffset = 5f;

	public LuaPrintReader()
	{
		err = new PrintStream(errOutputStream);
		out = new PrintStream(outputStream);
	}

	public void install(Globals globals)
	{
		globals.STDERR = err;
		globals.STDOUT = out;
	}

	public void update()
	{
		synchronized (logContainer)
		{
			ListIterator<LogEntity> logIterator = logContainer.listIterator();
			while (logIterator.hasNext())
			{
				LogEntity log = logIterator.next();
				log.update();
				if (log.isExpired())
				{
					logIterator.remove();
					// replacement
					LogEntity replacement = log.getReplacer();
					if (replacement != null)
					{
						logIterator.add(replacement);
					}
				}
			}
		}
	}

	public void readBuffer()
	{
		readStream(errOutputStream, Color.RED, 0.05);
		readStream(outputStream, Color.BLACK, 0.25);
	}

	private void readStream(ByteArrayOutputStream stream, Color color, double fadeSpeed)
	{
		StringBuilder sb = new StringBuilder();
		byte[] outputBytes = stream.toByteArray();
		for (byte b : outputBytes)
		{
			if (b == '\n')
			{
				synchronized (logContainer)
				{
					logContainer.add(0, new LogMessage(sb.toString(), color, fadeSpeed));
				}
				sb.setLength(0);
			}
			else
			{
				sb.append((char) b);
			}
		}
		stream.reset();
	}

	public void draw(Screen screen, Graphics2D g2d)
	{
		Dimension screenSize = screen.getScreenSize();
		float y = (float) screenSize.getHeight() - bottomOffset;
		synchronized (logContainer)
		{
			for (LogEntity log : logContainer)
			{
				log.draw(g2d, leftOffset, y);
				y -= log.getHeight();
			}
		}
	}
}
