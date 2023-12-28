package net.sf.uadetector.internal;

import net.sf.uadetector.exception.IllegalEmptyArgumentException;
import net.sf.uadetector.exception.IllegalNegativeArgumentException;
import net.sf.uadetector.exception.IllegalNullArgumentException;
import net.sf.uadetector.exception.IllegalStateOfArgumentException;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Check
{
	private Check()
	{
		//No config required
	}

	public static <T> T notEmpty(T type, String... message)
	{
		notNull(type, message);
		if (type.toString()
		        .isEmpty())
		{
			throw new IllegalEmptyArgumentException("Empty param : " + renderMessage(message));
		}
		return type;
	}

	public static <T> T notNull(T type, String... message)
	{
		if (type == null)
		{
			throw new IllegalNullArgumentException("Argument '" + renderMessage(message) + "' must not be null.");
		}
		return type;
	}

	private static String renderMessage(String... message)
	{
		if (message.length == 0)
		{
			return "";
		}
		if (message.length == 1)
		{
			return message[0];
		}

		String[] params = Arrays.copyOfRange(message, 1, message.length);

		return String.format(message[0], (Object[]) params);
	}

	public static <T extends Number> T notNegative(T type, String... message)
	{
		notNull(type, message);
		if (type.intValue() < 0)
		{
			throw new IllegalNegativeArgumentException("Negative : " + renderMessage(message));
		}
		return type;
	}

	public static <T extends Character> T notNegative(T type, String... message)
	{
		notNull(type, message);
		if ((char) type < 0)
		{
			throw new IllegalNegativeArgumentException("Negative : " + renderMessage(message));
		}
		return type;
	}

	public static boolean isDebugEnabled()
	{
		return Logger.getLogger("")
		             .isLoggable(Level.FINE);
	}

	public static boolean stateIsTrue(boolean bool, String... message)
	{
		if (!bool)
		{
			throw new IllegalStateOfArgumentException(renderMessage(message));
		}
		return bool;
	}
}
