import org.jschool.cachablefibonachi.CalculatorImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class RunTest {

    public static void main(String[] args) throws SQLException {
        new CalculatorImpl().fibonachi(8).forEach(System.out::println);

        String url      = "jdbc:h2:~/resources/test";   //database specific url
        String user     = "sa";
        String password = "";

        Connection connection =
                DriverManager.getConnection(url, user, password);

        Statement statement = connection.createStatement();
        String sql =
                "CREATE TABLE PEOPLE (id  INT PRIMARY KEY AUTO_INCREMENT NOT NULL, fib INT NOT NULL);";

        statement.execute(sql);
        statement.close();
        connection.close();
    }
}
