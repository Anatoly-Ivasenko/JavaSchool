package org.jscholl.reflection.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Данный класс реализует измеряющий прокси-интерфейс.
 * Если у метода проксируемого интерфейса указана аннотация @Metric,
 * То в консоль будет выведено время выполнения метода в наносекундах
 */
public class PerformanceProxy implements InvocationHandler {
    private Object delegate;

    public PerformanceProxy(Object delegate) {
        this.delegate = delegate;
    }


    /**
     * Возвращает объект который реализует интерфейсы класса указанного объекта
     * @param delegate указанный объект
     */
    public static <T> T proxyFactory(Object delegate) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                delegate.getClass().getInterfaces(),
                new PerformanceProxy(delegate));
    }

    /**
     * Реализация метода invoke интерфейса {@see InvocationHandler}, которая при наличии аннотации @Metric
     * производит замер времени выполнения метода и выводит его в консоль
     *
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        Annotation[] annotations = method.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Metric) {
                long start = System.nanoTime();
                Object object = method.invoke(delegate, args);
                System.out.println("Время работы метода: " + (System.nanoTime() - start) + " наносекунд");
                return object;
            }

        }
        Object object = method.invoke(delegate, args);
        return object;
    }
}
