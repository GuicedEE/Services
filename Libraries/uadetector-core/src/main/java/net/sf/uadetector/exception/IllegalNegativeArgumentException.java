package net.sf.uadetector.exception;

public class IllegalNegativeArgumentException
		extends RuntimeException
{
	public IllegalNegativeArgumentException()
	{
	}

	public IllegalNegativeArgumentException(String message)
	{
		super(message);
	}

	public IllegalNegativeArgumentException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public IllegalNegativeArgumentException(Throwable cause)
	{
		super(cause);
	}

	public IllegalNegativeArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
