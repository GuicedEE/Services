package org.aopalliance.intercept;

/**
 * 拦截目标对象的域访问操作.
 *
 * 用户应该实现{@link #set(FieldAccess)}方法和
 * {@link #get(FieldAccess)}来修改原始行为.
 * 例如,以下类实现了跟踪拦截器(跟踪被拦截域的访问):
 * <pre>
 * class TracingInterceptor implements FieldInterceptor {
 *
 *   Object set(FieldAccess fa) throws Throwable {
 *     System.out.println("field "+fa.getField()+" is set with value "+
 *                        fa.getValueToSet());
 *     Object ret=fa.proceed();
 *     System.out.println("field "+fa.getField()+" was set to value "+ret);
 *     return ret;
 *   }
 *
 *   Object get(FieldAccess fa) throws Throwable {
 *     System.out.println("field "+fa.getField()+" is about to be read");
 *     Object ret=fa.proceed();
 *     System.out.println("field "+fa.getField()+" was read; value is "+ret);
 *     return ret;
 *   }
 * }
 * </pre>
 *
 * @author daniel lee
 */
public interface FieldInterceptor extends Interceptor {

    /**
     * 在field获取前、后实现操作.
     * 优雅的实现会调用{@link Joinpoint#proceed()}(内部封装本方法)来实现.
     *
     * @param fieldRead 域READ相关的连接点
     * @return 域获取{@link Joinpoint#proceed()}的结果,可能被拦截器所拦截.
     * @throws Throwable 如果拦截器或目标对象抛出异常.
     */
    Object get(FieldAccess fieldRead) throws Throwable;

    /**
     * 在field设置前、后实现操作.
     * 优雅的实现会调用{@link Joinpoint#proceed()}(内部封装本方法)来实现.
     *
     * @param fieldWrite 域WRITE相关的连接点
     * @return 域设置{@link Joinpoint#proceed()}的结果,可能被拦截器所拦截.
     * @throws Throwable 如果拦截器或目标对象抛出异常.
     */
    Object set(FieldAccess fieldWrite) throws Throwable;

}
