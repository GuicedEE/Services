
package org.aopalliance.intercept;

/**
 * 此接口表示程序中的一次方法调用.
 * 一次调用就是一个连接点,并能够被拦截器所拦截.
 *
 * @author daniel lee
 */
public interface Invocation extends Joinpoint {

   /**
    * 获取以对象数组形式传入的参数.
    * 可以通过改变这个数组中的元素值来修改参数.
    *
    * @return 调用参数
    */
   Object[] getArguments();

}
