
package org.aopalliance.intercept;

import java.lang.reflect.Field;

/**
 * 本接口表示程序中的域访问.
 *
 * @see FieldInterceptor
 */
public interface FieldAccess extends Joinpoint {

    /** READ访问类型(看{@link #getAccessType()}).   */
    int READ   =  0;

    /** WRITE访问类型 (看{@link #getAccessType()}). */
    int WRITE  =  1;

    /**
     * 获取正在被访问的域.
     *
     * 本方法是{@link Joinpoint#getStaticPart()}方法的友好实现.
     * 两者具有相同返回结果.
     *
     * @return the field being accessed.
     */
    Field getField();

    /**
     * 获取应设置到域中的值
     * 这个值可以被拦截到并且被拦截器改变值.
     */
    Object getValueToSet();

    /**
     * 返回访问类型.
     *
     * @return FieldAccess.READ || FieldAccess.WRITE
     */
    int getAccessType();

}

