package org.jschool.cachableList.datasources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DB extends Source {

    @Override
    public Connection connection() throws SQLException {
        final Connection connection = DriverManager.getConnection("jdbc:h2:~/resources/test", "sa", "");
        connection.setAutoCommit(true);
        return connection;
    }
}
