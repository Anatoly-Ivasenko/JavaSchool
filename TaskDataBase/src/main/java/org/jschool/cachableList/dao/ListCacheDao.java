package org.jschool.cachableList.dao;

import java.util.List;

public interface ListCacheDao {

    void addToCache(List<Integer> value);

    List<Integer> getListFromCache(int arg);

    int getLastArgInCache();
}
