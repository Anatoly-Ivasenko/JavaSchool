package org.jschool.cachableList.datasources;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Source {

     public abstract Connection connection() throws SQLException;
}
