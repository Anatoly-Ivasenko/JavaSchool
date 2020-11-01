package org.jschool.cachableList.dao;

import org.jschool.cachableList.datasources.Source;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ListCacheDaoFactory {
    private static final Map<String, ListCacheDao> cacheDaoMap= new ConcurrentHashMap<>();

    public static ListCacheDao getListCacheDao(Class<? extends Source> dataSourceType, String tableName) throws Exception {
        String daoUid = dataSourceType.hashCode() + tableName;
        if (cacheDaoMap.containsKey(daoUid)) {
            return cacheDaoMap.get(daoUid);
        } else {
            Source source = dataSourceType.newInstance();
            ListCacheDao dao = new ListCacheDaoImpl(source, tableName);
            cacheDaoMap.put(daoUid, dao);
            return dao;
        }
    }
}
