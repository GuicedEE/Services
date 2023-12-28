package gnu.io;

public class NRSerialPortException extends RuntimeException
{
	public NRSerialPortException()
	{
	}
	
	public NRSerialPortException(String message)
	{
		super(message);
	}
	
	public NRSerialPortException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public NRSerialPortException(Throwable cause)
	{
		super(cause);
	}
	
	public NRSerialPortException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
