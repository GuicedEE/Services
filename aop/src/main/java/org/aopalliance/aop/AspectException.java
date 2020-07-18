package org.aopalliance.aop;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * (*** 异常封装可借鉴)
 * 所有AOP基础异常的超类.
 * 设计为未检查异常(继承{@link RuntimeException}),
 * 原因是切面异常是致命的,终端用户代码不应该被迫去捕获这类异常.
 *
 * @author daniel lee
 */
public class AspectException
		extends RuntimeException
{

	// 异常信息
	private final String message;

	// 栈轨迹
	private final String stackTrace;

	// 根异常
	private final Throwable t;

	public AspectException(String str)
	{
		// 委托给父类打印
		super(str);

		// 保存本类异常信息和异常栈轨迹
		this.message = str;
		this.stackTrace = str;
		t = null;
	}

	/**
	 * 本类message信息委托父类输出. 本类打印自身异常栈轨迹.
	 *
	 * @param str
	 * 		异常信息
	 * @param t
	 * 		异常对象
	 */

	public AspectException(String str, Throwable t)
	{
		// 组装message并委托给父类处理
		super(str + "; nested exception is " + t.getMessage());

		this.t = t;
		StringWriter out = new StringWriter();
		t.printStackTrace(new PrintWriter(out));  // 打印栈轨迹
		this.message = str;
		this.stackTrace = out.toString();
	}

	/**
	 * 异常根原因.可以为null.
	 *
	 * @return 根异常
	 */
	@Override
	public Throwable getCause()
	{
		return t;
	}

	@Override
	public String getMessage()
	{
		return this.message;
	}

	public String toString()
	{
		return this.getMessage();
	}

	/**
	 * 默认是标准错误输出栈轨迹
	 * 注意: 在catch语句中,此时异常对象已被初始化.
	 */
	@Override
	public void printStackTrace()
	{
		System.err.print(this.stackTrace);
	}

	/**
	 * @param out
	 * 		打印字节流
	 */
	@Override
	public void printStackTrace(PrintStream out)
	{
		// 字节流转字符流后再在控制台输出
		printStackTrace(new PrintWriter(out));
	}

	/**
	 * @param out
	 * 		打印字符流
	 */
	@Override
	public void printStackTrace(PrintWriter out)
	{
		out.print(this.stackTrace);
	}

}
