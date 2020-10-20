package org.jschool.concurrentcacheproxy;

import java.lang.reflect.Proxy;

/**
 * Данный класс реализует кэширующий прокси, позволяющий кэшировать результаты работы методов интерфейсов
 * в память JVM, также в файловую систему (если результат является экземпяром сериализуемого класса).
 * Кэширование методов осуществляется при наличии у метода аннотации @Cache {@see Cache.class}.
 *
 */
public class CacheProxy {

    private final String cacheRootDirectory;

    public CacheProxy(String cacheRootDirectory) {
        this.cacheRootDirectory = cacheRootDirectory;
    }

    /**
     * Возвращает кэшированную версию указанного объекта. Кэширование осуществляется в память JVM или в файловую
     * систему в папку cacheRootDirectory, в соответствии с настройками указанными в парамтрах аннотации @Cache,
     * или настройками по умолчанию. Вызов методов и кэширование осуществляется
     * в классе CacheKeeper (имплементирует InvocationHandler)
     *
     * @param cachedObject указанный объект
     */
    public <T> T cache(Object cachedObject) {
        return (T) Proxy.newProxyInstance(cachedObject.getClass().getClassLoader(),
                cachedObject.getClass().getInterfaces(), new CacheKeeper(cachedObject, cacheRootDirectory));
    }
}



