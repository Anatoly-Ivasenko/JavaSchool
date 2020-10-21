package org.jschool.concurrentcacheproxy;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;

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

    /**
     * Реализовывает проксирование: вызов методов проксируемеого интерфейса, запись / считывание кэша, хранимого
     * в памяти JVM, и вызов статических методов CacheUtils для записи / считывания кэша, хранимого в ФС.
     * Принимает настройки корневой папки для хранения кэша в ФС, экземпляр класса, методы которого мы кэшируем.
     * Является хранилищем кэша в памяти JVM, хранения организовано в Map.
     *
     */
    private static class CacheKeeper implements InvocationHandler {

        /**
         * Экземпляр класса, методы которого мы кэшируем
         */
        private final Object cachedObject;

        /**
         * Корневая папка для хранения кэша в ФС
         */
        private final String cacheRootDir;

        /**
         * Хранилище кэша в памяти JVM. Для обеспечения потокобезопасности используется ConcurrentHashMap.
         */
        private final ConcurrentMap<String, Object> cacheInMemory = new ConcurrentHashMap<>();

        private final ConcurrentMap<String, Semaphore> filesSemaphore = new ConcurrentHashMap<>();

        CacheKeeper(Object cachedObject, String cacheRootDir) {
            this.cachedObject = cachedObject;
            this.cacheRootDir = cacheRootDir;
        }

        /**
         * Метод обеспечивающий проверку наличия кэша, вызов метода (если кэша нет или кэширование не предусмотрено),
         * запись результатов в кэш и возврат результата (из кэша или расчитанного). Запись/считывание кэша, хранимого
         * в памяти JVM реализовано тут, запись/считывание кэша, хранимого в ФС реализовано в классе CacheUtils.
         *
         * @param proxy
         * @param method    Метод, проксируемего интерфейса
         * @param args      Массив объектов - аргументов метода method
         * @return Object   Результат работы метода
         * @throws Exception Бросает исключения связанные с некоректным вызовом методов, работой с ФС, сериализацией.
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
            CacheSettings cacheSettings = new CacheSettings(method);
            if (!cacheSettings.cached()) {
                return method.invoke(cachedObject,args);            //Если нет аннотации @Cache вызываем метод и возвращаем результат
            }

            String keyCache = cacheSettings.keyGenerator(args);

            if (cacheSettings.inFile()) {
                File cacheFile = Paths.get(cacheRootDir, keyCache + ".cache").toFile();
                Semaphore semaphore;
                synchronized (filesSemaphore) {
                    if (filesSemaphore.containsKey(keyCache)) {
                        semaphore = filesSemaphore.get(keyCache);
                    } else {
                        semaphore = new Semaphore(1);
                        filesSemaphore.put(keyCache, semaphore);
                    }
                }
                semaphore.acquire();
                if (!cacheFile.exists()) {
                    Object result = method.invoke(cachedObject, args);
                    if (cacheSettings.toZip()) {
                        CacheUtils.saveToZip(CacheUtils.trimResult(result, cacheSettings), cacheSettings, cacheFile);
                    } else {
                        CacheUtils.save(CacheUtils.trimResult(result, cacheSettings), cacheSettings, cacheFile);
                    }
                    semaphore.release();
                    return result;
                }
                if (cacheSettings.toZip()) {
                    Object result = CacheUtils.getCacheFromZip(cacheFile);
                    semaphore.release();
                    return result;
                }
                Object result = CacheUtils.getCache(cacheFile);
                semaphore.release();
                return result;


            }

            if (cacheInMemory.containsKey(keyCache)) {
                return cacheInMemory.get(keyCache);
            }

            Object result = method.invoke(cachedObject, args);
            cacheInMemory.put(keyCache, CacheUtils.trimResult(result, cacheSettings));
            return result;
        }
    }
}



