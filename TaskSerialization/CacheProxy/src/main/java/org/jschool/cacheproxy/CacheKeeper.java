package org.jschool.cacheproxy;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CacheKeeper implements InvocationHandler {
    private final Object cachedObject;
    private final String cacheRootDir;
    private final Map<String, Object> cacheInMemory = new HashMap<>();

    public CacheKeeper(Object cachedObject, String cacheRootDir) {
        this.cachedObject = cachedObject;
        this.cacheRootDir = cacheRootDir;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        CacheAnnotationResolver cacheSettings = new CacheAnnotationResolver(method);
        if (!cacheSettings.cached()) {
            return method.invoke(cachedObject,args);            //Если нет аннотации @Cache вызываем метод и возвращаем результат
        }

        String keyCache = keyGenerator(cacheSettings, args);

        if (cacheSettings.inFile()) {
            File cacheFile = Paths.get(cacheRootDir, keyCache + ".cache").toFile();

            if (!cacheFile.exists()) {
                Object result = method.invoke(cachedObject, args);
                CacheUtils.save(result, cacheSettings, cacheFile);
                return result;
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

    private String keyGenerator(CacheAnnotationResolver cacheSettings, Object[] args) {
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(cacheSettings.keyPrefix());
        Class<?>[] parameterTypes = cacheSettings.getMethodParameterTypes();

        if (cacheSettings.argsForKey().length == 0) {
            for (int i = 0; i < parameterTypes.length; i++) {
                keyBuilder.append("_").append(parameterTypes[i].getName()).append(args[i].hashCode());
            }
            return keyBuilder.toString();
        }

//        for (int i = 0; i < parameterTypes.length; i++) {
//            for (int j = 0; j < cacheSettings.argsForKey().length; j++) {
//                // TODO Логика выбора аргументов для формирования ключа
//            }
//        }

        return keyBuilder.toString();
    }

}
