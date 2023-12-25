package org.aopalliance.intercept;


import org.aopalliance.aop.Advice;

/**
 * 本接口代表通用拦截器.
 *
 * 通用拦截器能够拦截发生在程序中的运行时事件.
 * 这些事件通过(指定)连接点来实现.运行时连接点可以是调用、域访问等.
 *
 * 本接口不能直接被使用. 而是使用子接口来拦截特定事件.
 * 例如,以下类为了实现调试器而实现了一些特定的拦截器:
 *
 * <pre>
 * class DebuggingInterceptor implements MethodInterceptor,
 *     ConstructorInterceptor, FieldInterceptor {
 *
 *
 *   Object invoke(MethodInvocation i) throws Throwable {
 *     debug(i.getMethod(), i.getThis(), i.getArgs());
 *     return i.proceed();
 *   }
 *
 *
 *   Object construct(ConstructorInvocation i) throws Throwable {
 *     debug(i.getConstructor(), i.getThis(), i.getArgs());
 *     return i.proceed();
 *   }
 *
 *
 *   Object get(FieldAccess fa) throws Throwable {
 *     debug(fa.getField(), fa.getThis(), null);
 *     return fa.proceed();
 *   }
 *
 *
 *   Object set(FieldAccess fa) throws Throwable {
 *     debug(fa.getField(), fa.getThis(), fa.getValueToSet());
 *     return fa.proceed();
 *   }
 *
 *   void debug(AccessibleObject ao, Object this, Object value) {
 *     ...
 *   }
 * }
 * </pre>
 *
 * @author daniel lee
 * @see Joinpoint
 */
public interface Interceptor extends Advice {
}
