package net.sf.uadetector.exception;

public class IllegalStateOfArgumentException
		extends RuntimeException
{
	public IllegalStateOfArgumentException()
	{
	}

	public IllegalStateOfArgumentException(String message)
	{
		super(message);
	}

	public IllegalStateOfArgumentException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public IllegalStateOfArgumentException(Throwable cause)
	{
		super(cause);
	}

	public IllegalStateOfArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
