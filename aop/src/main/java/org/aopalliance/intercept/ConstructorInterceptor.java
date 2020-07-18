package org.aopalliance.intercept;

/**
 * 用于拦截新建对象的构造器.
 *
 * 用户应该实现{@link #construct(ConstructorInvocation)}方法来修改原始行为.
 * 例如,以下类实现了一个单例拦截器(允许仅一个唯一实例用于被拦截的类):
 *
 * <pre class=code>
 * class DebuggingInterceptor implements ConstructorInterceptor {
 *   Object instance=null;
 *
 *   Object construct(ConstructorInvocation i) throws Throwable {
 *     if(instance==null) {
 *       return instance=i.proceed();
 *     } else {
 *       throw new Exception("singleton does not allow multiple instance.");
 *     }
 *   }
 * }
 * </pre>
 *
 * @author daniel lee
 */
@FunctionalInterface
public interface ConstructorInterceptor extends Interceptor {

    /**
     * 实现该方法会在新对象构造之前、之后执行额外的操作.
     * 优雅的实现会调用{@link Joinpoint#proceed()}(内部封装本方法)来实现.
     *
     * @param invocation 方法调用连接点.
     * @return 新创建的对象, 对象通常调用{@link Joinpoint#proceed()}的结果,可能被拦截器所替代.
     * @throws Throwable 如果拦截器或目标对象抛出异常.
     */
    Object construct(ConstructorInvocation invocation) throws Throwable;
}
