import org.jschool.cachableList.cacheproxy.Cachable;
import org.jschool.cachableList.cacheproxy.CacheProxy;
import org.jschool.cachableList.datasources.H2DB;
import org.jschool.cachableList.datasources.Source;

import java.sql.*;

public class RunTest {

    private static final String CREATE_TABLE = "set schema public;\n create table fibonachi (\n" +
            "    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
            "    value INT NOT NULL\n" +
            ");";

    public static void main(String[] args) throws SQLException {

//        Source source = new H2DB();
//
//        try (Statement statement = source.connection().createStatement()) {
////            statement.setString(1,"fibonachi");
//            statement.execute(CREATE_TABLE);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        Calculator calculator = new CacheProxy().cache(new CalculatorImpl());
        System.out.println("3:");
        calculator.fibonachi(3).forEach(System.out::println);
        System.out.println();
        System.out.println("7:");
        calculator.fibonachi(7).forEach(System.out::println);
        System.out.println();
        System.out.println("4:");
        calculator.fibonachi(4).forEach(System.out::println);
        System.out.println();
        System.out.println("25:");
        calculator.fibonachi(25).forEach(System.out::println);
    }
}
