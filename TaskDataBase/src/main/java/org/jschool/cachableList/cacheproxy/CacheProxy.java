package org.jschool.cachableList.cacheproxy;

import org.jschool.cachableList.dao.ListCacheDao;
import org.jschool.cachableList.dao.ListCacheDaoFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class CacheProxy {

    public <T> T cache(Object cachedObject) {
        return (T) Proxy.newProxyInstance(cachedObject.getClass().getClassLoader(),
                cachedObject.getClass().getInterfaces(), new CacheHandler(cachedObject));
    }

    static class CacheHandler implements InvocationHandler {

        private final Object cachedObject;

        public CacheHandler(Object cachedObject) {
            this.cachedObject = cachedObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Exception {

            if (!method.isAnnotationPresent(Cachable.class)) {
                return method.invoke(cachedObject,args);
            }

            ListCacheDao listCacheDao = ListCacheDaoFactory.getListCacheDao(method.getAnnotation(Cachable.class).value(), method.getName());

            int arg = (int) args[0];

            if (listCacheDao.getLastArgInCache() >= arg) {
                return listCacheDao.getListFromCache(arg);
            } else {
                System.out.println("compute");
                List<Integer> result = (List<Integer>) method.invoke(cachedObject,args);
                listCacheDao.addToCache(result);
                return result;
            }
        }
    }
}



