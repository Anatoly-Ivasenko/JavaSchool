package org.jscholl.reflection.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Данный класс реализует кэширующий прокси.
 * В качестве Кэша используется Map(Integer, Object) для хранения ранее вычисленных значений.
 * В качестве ключа используется сумма хэшей объектов-аргументов проксируемого интерфейса.
 * В данном случае реализовано проксирование интерефейса {@see Calculator} , содержащего всего один метод.
 * В случае если методов будет больше, тогда необходимо создать более сложную структуру хранения данных включая
 * хранение вызываемых методов, либо создать класс, который будет реализовывать уникальные объекты из вызываемого
 * метода и переданного массива аргументов этого метода; а также пересмотреть алгоритм метода  invoke в части
 * сравнения входных данных с кэшем.
 *
 */

public class CachingInvocationHandler implements InvocationHandler {
    private Object delegate;
    final static Map<Integer, Object> cacheMap = new HashMap<>();     //Хранилище кэша

    public CachingInvocationHandler(Object delegate) {
        this.delegate = delegate;
    }


    /**
     * Возвращает объект который реализует интерфейсы класса указанного объекта
     * @param delegate указанный объект
     */
    public static <T> T proxyFactory(Object delegate) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                delegate.getClass().getInterfaces(),
                new CachingInvocationHandler(delegate));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        Annotation[] annotations = method.getDeclaredAnnotations();
        Integer argsHash = argsHash(args);
        for (Annotation annotation : annotations) {
            if ((annotation instanceof Cache) && (cacheMap.containsKey(argsHash))) {
                return cacheMap.get(argsHash);
            }
        }
        Object object = method.invoke(delegate,args);
        cacheMap.put(argsHash, object);
        System.out.println("-compute-");
        return object;
    }

    /**
     * Возвращает сумму хэшей указанного массива объектов
     * @param args  Object[] массив объектов для рассчеты суммы хэшей
     * @return  Integer сумма хэшей указанного массива объектов
     */
    private static Integer argsHash (Object[] args) {
        int hash = 0;
        for(Object arg : args) {
            hash = hash + (arg == null ? 0 : arg.hashCode());
        }
        return hash;
    }
}
