package org.jschool.cachableList.dao;

import org.jschool.cachableList.datasources.Source;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class ListCacheDaoImpl implements ListCacheDao {
    private static final String INSERT_VALUE_IN_CACHE_TABLE = "insert into fibonachi (value) values (?);";
    private static final String GET_CACHE_TABLE = "select * from fibonachi;";
    private static final String GET_LAST_ID = "select id from fibonachi order by id desc limit 1;";
    private static final String CREATE_TABLE = "set schema public;\n" +
                                                "create table fibonachi (\n" +
                                                "    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
                                                "    value INT NOT NULL\n" +
                                                ");";
    private final String tableName;
    private final Source source;

    public ListCacheDaoImpl(Source source, String tableName){
        this.tableName = tableName;
        this.source = source;
    }

    @Override
    public void addToCache(List<Integer> list) {
        List<Integer> valuesForAdding = list.subList(getLastArgInCache(), list.size());
        try (PreparedStatement statement = source.connection().prepareStatement(INSERT_VALUE_IN_CACHE_TABLE)) {
            for (Integer value : valuesForAdding) {
                statement.setInt(1, value);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Integer> getListFromCache(int arg) {
        List<Integer> listFromCache = new ArrayList<>();
        try (Statement statement = source.connection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_CACHE_TABLE);
            while (resultSet.next()) {
                listFromCache.add(resultSet.getInt(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listFromCache.subList(0, arg);
    }

    @Override
    public int getLastArgInCache() {
        int result = 0;
        try (Statement statement = source.connection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_LAST_ID);
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void createTable(String tableName) {
        try (Statement statement = source.connection().createStatement()) {
            statement.execute(MessageFormat.format(CREATE_TABLE, tableName));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
