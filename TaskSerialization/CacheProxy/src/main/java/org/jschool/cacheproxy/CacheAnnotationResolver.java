package org.jschool.cacheproxy;

import java.lang.reflect.Method;
import java.util.List;

public class CacheAnnotationResolver {
    private final Method method;


    public CacheAnnotationResolver(Method method) {
        this.method = method;
    }

    boolean cached() {
        return (method.isAnnotationPresent(Cache.class));
    }

    boolean inFile() {
        return (method.getAnnotation(Cache.class).cacheType() == CacheType.IN_FILE);
    }

    String keyPrefix() {
        if (method.getAnnotation(Cache.class).namePrefix().equals("")) {
            return method.getName();
        }
        else {
            return method.getAnnotation(Cache.class).namePrefix();
        }
    }

    Class<?>[] getMethodParameterTypes() {
        return method.getParameterTypes();
    }

    boolean resultIsAssignableToList() {
        Class<?>[] interfaces = method.getReturnType().getInterfaces();
        for (Class<?> iface : interfaces) {
            if (iface.equals(List.class)){
                return true;
            }
        }
        return false;
    }

    boolean toZip() {
        return (method.getAnnotation(Cache.class).zipped());
    }

    Class<?>[] argsForKey() {
        return (method.getAnnotation(Cache.class).identityBy());
    }

    int listCapacity() {
        return (method.getAnnotation(Cache.class).listCapacity());
    }

    String getMethodName() {
        return method.getName();
    }
}
