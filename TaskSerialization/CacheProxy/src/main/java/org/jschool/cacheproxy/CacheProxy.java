package org.jschool.cacheproxy;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;


/**
 * Данный класс реализует кэширующий прокси.
 *
 *
 * TODO необходимо создать более сложную структуру хранения данных включая
 * хранение вызываемых методов, либо создать класс, который будет реализовывать уникальные объекты из вызываемого
 * метода и переданного массива аргументов этого метода; а также пересмотреть алгоритм метода  invoke в части
 * сравнения входных данных с кэшем.
 *
 */
public class CacheProxy { // ? , Serializable{

    final String cacheRootDirectory;
    final Map<String, Map<Integer, Object>> cacheInMemory = new HashMap<>();

    /**
     * Возвращает объект который реализует кэширование в ...
     *
     * @param cacheRootDirectory    путь к директории для хранения кэшированных данных
     */

    public CacheProxy(String cacheRootDirectory) {
        this.cacheRootDirectory = cacheRootDirectory;
    }

    /**
     * Возвращает кэшированную версию указанного объекта
     * @param cachedObject указанный объект
     */
    public <T> T cache(Object cachedObject) {
        return (T) Proxy.newProxyInstance(cachedObject.getClass().getClassLoader(),
                cachedObject.getClass().getInterfaces(), new CachingInvocationHandler(cachedObject, this));
    }
}



