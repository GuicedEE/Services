package org.aopalliance.intercept;

import java.lang.reflect.Method;

/**
 * 方法拦截器.
 * 一次方法调用是一个连接点,并能够被方法拦截器所拦截.
 *
 * @author daniel lee
 * @see MethodInterceptor
 */
public interface MethodInvocation extends Invocation
{

    /**
     * 获取正被调用的方法.
     *
     * 本方法是{@link Joinpoint#getStaticPart()}方法的友好实现.
     * 两者具有相同返回结果.
     *
     * @return 正被调用的方法.
     */
    Method getMethod();

}

