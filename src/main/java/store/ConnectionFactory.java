package store;

import java.sql.SQLException;

public interface ConnectionFactory {
    java.sql.Connection getConnection() throws SQLException;
}
