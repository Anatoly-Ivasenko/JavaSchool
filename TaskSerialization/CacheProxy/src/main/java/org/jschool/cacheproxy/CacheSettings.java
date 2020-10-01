package org.jschool.cacheproxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Реализует удобный доступ к настройкам кэша
 */

public class CacheSettings {
    private final Method method;

    public CacheSettings(Method method) {
        this.method = method;
    }

    /**
     * Возвращает true если метод содержит аннотацию @Cache
     * @return boolean true если метод содержит аннотацию @Cache
     */
    boolean cached() {
        return (method.isAnnotationPresent(Cache.class));
    }

    /**
     * Возвращает true если кэширование необходимо производить в ФС
     * @return boolean true если кэширование необходимо производить в ФС
     */
    boolean inFile() {
        return (method.getAnnotation(Cache.class).cacheType() == CacheType.IN_FILE);
    }

    /**
     * Возвращает префикс для ключа/имени файла кэша
     * @return String   префикс для ключа/имени файла кэша
     */
    String keyPrefix() {
        if (method.getAnnotation(Cache.class).namePrefix().equals("")) {
            return method.getName();
        }
        else {
            return method.getAnnotation(Cache.class).namePrefix();
        }
    }

    /**
     * Возвращает массив классов параметров кэшируемого объекта
     * @return  Class[] классы параметров кэшируемого объекта
     */
    Class<?>[] getMethodParameterTypes() {
        return method.getParameterTypes();
    }

    /**
     * Возвращает true если результат метода имплементирует интерфейс List
     * @return  boolean true если результат метода имплементирует интерфейс List
     */
    boolean resultIsAssignableToList() {
        Class<?>[] interfaces = method.getReturnType().getInterfaces();
        for (Class<?> iface : interfaces) {
            if (iface.equals(List.class)){
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает true если кэш нужно сжимать (используется при хранении в ФС)
     * @return boolean true если кэш нужно сжимать
     */
    boolean toZip() {
        return (method.getAnnotation(Cache.class).zipped());
    }

    /**
     * Возвращает массив классов параметров, которые необходимо учитывать
     * при создании ключа/имени файла
     * @return  Class[]
     */
    Class<?>[] argsForKey() {
        return (method.getAnnotation(Cache.class).identityBy());
    }

    /**
     * Возвращает емкость списка для хранения в кэше (для List'ов)
     * @return int  емкость списка List для хранения
     */
    int listCapacity() {
        return (method.getAnnotation(Cache.class).listCapacity());
    }

    /**
     * Возвращает имя метода
     * @return  String имя метода
     */
    String getMethodName() {
        return method.getName();
    }
}
