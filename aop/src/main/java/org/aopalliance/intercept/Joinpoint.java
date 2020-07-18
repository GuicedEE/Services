
package org.aopalliance.intercept;

import java.lang.reflect.AccessibleObject;

/**
 * 此接口表示一个通用的运行时"连接点"(AOP术语).
 * 运行时连接点是一个发生在静态连接点上的事件(程序中的某个位置).
 *
 * @author daniel lee
 * @see Interceptor
 */
public interface Joinpoint {

   /**
    * 继续处理拦截器链中的下一个拦截器.
    *
    * @return 具体得看子接口中的proceed如何处理.
    * @throws Throwable 如果连接点抛出异常.
    */
   Object proceed() throws Throwable;

   /**
    * 返回持有当前连接点静态部分的对象.
    * 例如,调用的目标对象.
    *
    * @return 如果可访问对象是静态的,则返回null;否则返回当前对象.
    */
   Object getThis();

   /**
    * 返回连接点的静态部分.
    * 静态部分是一个可访问对象,且这些可访问对象已经安装了拦截器链
    */
   AccessibleObject getStaticPart();

}
