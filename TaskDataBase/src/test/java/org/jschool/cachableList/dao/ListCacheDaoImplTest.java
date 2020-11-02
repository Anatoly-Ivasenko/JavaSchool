package org.jschool.cachableList.dao;

import org.jschool.cachableList.Calculator;
import org.jschool.cachableList.CalculatorImpl;
import org.jschool.cachableList.cacheproxy.CacheProxy;
import org.jschool.cachableList.datasources.H2DB;
import org.junit.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ListCacheDaoImplTest {
    private final Calculator calc = new CacheProxy().cache(new CalculatorImpl());
    private final static String
            CREATE_TABLE = "create table if not exists fibonachi (\n id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,\n value INT NOT NULL\n)";
    private final static String
            DROP_TABLE = "drop table if exists fibonachi";

    @Before
    public void setUpCreateTable() throws SQLException {
        Statement statement = new H2DB().connection().createStatement();
        statement.execute(DROP_TABLE);
        statement.execute(CREATE_TABLE);
    }
    @After
    public void tearDownDropTable() throws SQLException {
        Statement statement = new H2DB().connection().createStatement();
        statement.execute(DROP_TABLE);
    }

    @Test
    public void addToCacheTest() throws SQLException {
        System.out.println("Add to Cache test");
        Calculator calc = new CacheProxy().cache(new CalculatorImpl());
        List<Integer> result = calc.fibonachi(11);
        System.out.println("11:");
        result.forEach(System.out::println);
        Statement statement = new H2DB().connection().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from fibonachi");
        List<Integer> listFromDB = new ArrayList<>();
        while (resultSet.next()) {
            listFromDB.add(resultSet.getInt(2));
        }
        assertArrayEquals(listFromDB.toArray(), result.toArray());
    }

    @Test
    public void getListFromCacheTest() throws SQLException {
        System.out.println("Get List from Cache Test");
        List<Integer> list = new ArrayList<>();
        PreparedStatement preparedStatement = new H2DB().connection().prepareStatement("insert into fibonachi (value) values (?)");
        for (int i = 1; i <=5; i++) {
            preparedStatement.setInt(1, i);
            preparedStatement.addBatch();
            list.add(i);
        }
        preparedStatement.executeBatch();
        Calculator calc = new CacheProxy().cache(new CalculatorImpl());
        calc.fibonachi(5).forEach(System.out::println);
        assertArrayEquals(calc.fibonachi(5).toArray(), list.toArray());
    }

}