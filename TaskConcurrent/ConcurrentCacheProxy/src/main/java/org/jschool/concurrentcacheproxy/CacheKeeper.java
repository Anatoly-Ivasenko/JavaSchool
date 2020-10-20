package org.jschool.concurrentcacheproxy;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Реализовывает проксирование: вызов методов проксируемеого интерфейса, запись / считывание кэша, хранимого
 * в памяти JVM, и вызов статических методов CacheUtils для записи / считывания кэша, хранимого в ФС.
 * Принимает настройки корневой папки для хранения кэша в ФС, экземпляр класса, методы которого мы кэшируем.
 * Является хранилищем кэша в памяти JVM, хранения организовано в Map.
 *
 */
public class CacheKeeper implements InvocationHandler {

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

    public CacheKeeper(Object cachedObject, String cacheRootDir) {
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

        String keyCache = keyGenerator(cacheSettings, args);

        if (cacheSettings.inFile()) {
            File cacheFile = Paths.get(cacheRootDir, keyCache + ".cache").toFile();

            if (!cacheFile.exists()) {
                Object result = method.invoke(cachedObject, args);
                if (cacheSettings.toZip()) {
                    CacheUtils.saveToZip(CacheUtils.trimResult(result, cacheSettings), cacheSettings, cacheFile);
                }
                else {
                    CacheUtils.save(CacheUtils.trimResult(result, cacheSettings), cacheSettings, cacheFile);
                }
                return result;
            }
            if (cacheSettings.toZip()) {
                return CacheUtils.getCacheFromZip(cacheFile);
            }
            return CacheUtils.getCache(cacheFile);
        }

        if (cacheInMemory.containsKey(keyCache)) {
            return cacheInMemory.get(keyCache);
        }

        Object result = method.invoke(cachedObject, args);
        cacheInMemory.put(keyCache, CacheUtils.trimResult(result, cacheSettings));
        return result;
    }

    /**
     * Возвращает ключ/имя файла кэшируемого результата.
     *
     * TODO Возможно целесообразно переместить этот метод в CacheSettings
     *
     * @param cacheSettings CacheSettings   объект настроек кэша
     * @param args          Object[]        массив аргументов
     * @return String   ключ/имя файла кэшируемого результата
     */
    private String keyGenerator(CacheSettings cacheSettings, Object[] args) {
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(cacheSettings.keyPrefix());
        Class<?>[] parameterTypes = cacheSettings.getMethodParameterTypes();

        if (cacheSettings.argsForKey().length == 0) {
            for (int i = 0; i < parameterTypes.length; i++) {
                keyBuilder.append("_").append(parameterTypes[i].getName()).append(args[i].hashCode());
            }
            return keyBuilder.toString();
        }

        int[] keyArgs = new int[cacheSettings.argsForKey().length];
        int argsForKeyCounter = 0;
        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameterTypes[i].equals(cacheSettings.argsForKey()[argsForKeyCounter])) {
                keyArgs[argsForKeyCounter] = i;
                argsForKeyCounter++;
            }
        }

        for (int keyArg : keyArgs) {
            keyBuilder.append("_").append(parameterTypes[keyArg].getName()).append(args[keyArg].hashCode());
        }

        return keyBuilder.toString();
    }
}
