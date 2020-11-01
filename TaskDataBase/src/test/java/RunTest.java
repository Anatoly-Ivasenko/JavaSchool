import org.jschool.cachableList.cacheproxy.CacheProxy;

import java.sql.*;

public class RunTest {

    public static void main(String[] args) throws SQLException {

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
