package org.jschool.cachablefibonachi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;
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

            String url      = "jdbc:h2:~/resources/test";   //database specific url
            String user     = "sa";
            String password = "";
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM FIBONACHI;";
            ResultSet resultSet = statement.executeQuery(sql);
            List<Integer> cacheFibo = null;
            while (resultSet.next()) {
                cacheFibo.add(resultSet.getInt(2));
            }
            statement.close();
            connection.close();

            if ((cacheFibo.size() - 1) > (int) args[0]) {
                return cacheFibo.subList(0, (int)args[0] - 1);
            }

            List<Integer> result = (List<Integer>) method.invoke(cachedObject,args);

            Connection connection1 = DriverManager.getConnection(url, user, password);
            PreparedStatement statement1 = connection1.prepareStatement("INSERT INTO FIBONACHI (fibonum) value (?);");
            List<Integer> writeToDB = result.subList(cacheFibo.size() - 1, result.size() - 1);
            writeToDB.forEach(fibonum -> {
                statement1.setInt(1, fibonum);
                statement1.addBatch();
            });
            statement1.execute();
            return result;

            //запросить последнюю запись в БД
            //если она меньше или равна аргументу вернуть данные из БД
            // иначе посчитать и дописать в БД недостающие записи и вернуть результат
        }
    }
}



