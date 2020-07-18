package net.sf.uadetector.exception;

public class IllegalEmptyArgumentException
		extends RuntimeException
{
	public IllegalEmptyArgumentException()
	{
	}

	public IllegalEmptyArgumentException(String message)
	{
		super(message);
	}

	public IllegalEmptyArgumentException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public IllegalEmptyArgumentException(Throwable cause)
	{
		super(cause);
	}

	public IllegalEmptyArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
