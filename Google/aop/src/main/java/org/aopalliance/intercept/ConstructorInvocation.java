package org.aopalliance.intercept;

import java.lang.reflect.Constructor;

/**
 * 一次构造器调用是一个连接点,并能够被构造器拦截器所拦截.
 *
 * @author daniel lee
 * @see ConstructorInterceptor
 */
public interface ConstructorInvocation extends Invocation {

    /**
     * 获取正被调用的构造器.
     *
     * 本方法是{@link Joinpoint#getStaticPart()}方法的友好实现.
     * 两者具有相同返回结果.
     *
     * @return 正被调用的构造器.
     */
    Constructor getConstructor();

}

