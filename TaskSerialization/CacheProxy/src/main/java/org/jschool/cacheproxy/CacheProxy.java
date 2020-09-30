package org.jschool.cacheproxy;

import java.lang.reflect.Proxy;

/**
 * Данный класс реализует кэширующий прокси.
 *
 *
 */
public class CacheProxy {

    private final String cacheRootDirectory;

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
        return (T) Proxy.newProxyInstance(cachedObject.getClass().getClassLoader(),
                cachedObject.getClass().getInterfaces(), new CacheKeeper(cachedObject, cacheRootDirectory));
    }
}



