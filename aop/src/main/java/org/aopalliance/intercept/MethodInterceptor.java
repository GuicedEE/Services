package org.aopalliance.intercept;

/**
 * Intercepts calls on an interface on its way to the target. These
 * are nested "on top" of the target.
 *  Intercepts在通往目标的路上调用一个接口。 这些
 *  被嵌套在目标的“顶部”。
 *
 * 用户应该实现{@link #invoke(MethodInvocation)}方法来修改原始行为.
 * 例如,以下类实现了跟踪拦截器(跟踪所有被拦截方法的调用):
 *
 * <pre>
 * class TracingInterceptor implements MethodInterceptor {
 *
 *   Object invoke(MethodInvocation i) throws Throwable {
 *     System.out.println("method "+i.getMethod()+" is called on "+
 *                        i.getThis()+" with args "+i.getArguments());
 *     Object ret=i.proceed();
 *     System.out.println("method "+i.getMethod()+" returns "+ret);
 *     return ret;
 *   }
 * }
 * </pre>
 *
 * @author daniel lee
 */
@FunctionalInterface
public interface MethodInterceptor extends Interceptor {

    /**
     * 实现该方法会在本次调用之前、之后执行额外的操作.
     * 优雅的实现会调用{@link Joinpoint#proceed()}(内部封装本方法)来实现.
     *
     * @param invocation 方法调用连接点.
     * @return 调用{@link Joinpoint#proceed()}的结果,可能被拦截器所拦截.
     * @throws Throwable 如果拦截器或目标对象抛出异常.
     */
    Object invoke(MethodInvocation invocation) throws Throwable;
}
