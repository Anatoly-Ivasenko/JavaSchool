package org.jschool.cacheproxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Данный класс реализует кэширующий прокси.
 *
 *
 */
public class CacheProxy { // ? , Serializable{

    private final String cacheRootDirectory;
    private final Set<Class<?>> classesInCache = new HashSet<>();      //Хранение классов, для которых создавались кэш-прокси
    private final Map<String, Object> cacheInMemory = new HashMap<>();


    /**
     * Возвращает объект который реализует кэширование в ...
     *
     * @param cacheRootDirectory путь к директории для хранения кэшированных данных
     */

    public CacheProxy(String cacheRootDirectory) {
        this.cacheRootDirectory = cacheRootDirectory;
    }

    /**
     * Возвращает кэшированную версию указанного объекта
     *
     * @param cachedObject указанный объект
     */
    public <T> T cache(Object cachedObject) {
        classesInCache.add(cachedObject.getClass());
        return (T) Proxy.newProxyInstance(cachedObject.getClass().getClassLoader(),
                cachedObject.getClass().getInterfaces(), new CachingInvocationHandler(cachedObject, this));
    }

    boolean containsCache(Method method, Object[] args, Class<?> cachedClass) {
        Annotation cacheAnnotation = method.getAnnotation(Cache.class);
        return (!classesInCache.contains(cachedClass) || )
    }

    private String keyGenerator(Method method, Object[] args) {
        String key = "";
        if (!method.getAnnotation(Cache.class).namePrefix().equals("")) {
            key = method.getAnnotation(Cache.class).namePrefix();
        }
        else {
            key = method.getName();
        }

        if (method.getAnnotation(Cache.class).identityBy().length > 0) {
            Class<?>[] classesOfArgs = method.getParameterTypes();
            // TODO Логика выбора аргументов для формирования ключа
        }
        return key;
    }

}



