package net.sf.uadetector.exception;

public class IllegalNullArgumentException
		extends RuntimeException
{
	public IllegalNullArgumentException()
	{
	}

	public IllegalNullArgumentException(String message)
	{
		super(message);
	}

	public IllegalNullArgumentException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public IllegalNullArgumentException(Throwable cause)
	{
		super(cause);
	}

	public IllegalNullArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
