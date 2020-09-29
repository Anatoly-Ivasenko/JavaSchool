package org.jschool.cacheproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CachingInvocationHandler implements InvocationHandler {
    private Object cachedObject;
    private CacheProxy cacheProxy;

    public CachingInvocationHandler (Object cachedObject, CacheProxy cacheProxy) {
        this.cachedObject = cachedObject;
        this.cacheProxy = cacheProxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        if (!method.isAnnotationPresent(Cache.class)) {
            return method.invoke(cachedObject,args);
        }
        if (cacheProxy.containsCache(method, args)) {
            return cacheProxy.getFromCache(method, args);
        }
        Object object = method.invoke(cachedObject,args);
        cacheProxy.putCache(method, args, object);
        return object;
    }
}
