package helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.DbConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class TestTools {
    private static final Logger LOG = LoggerFactory.getLogger(TestTools.class.getName());

    public static void clearTables() {
        try (Connection connection = DbConnectionFactory.instOf().getConnection();
             Statement statement = connection.createStatement()
        ) {
            for (String table : List.of("ticket", "account")) {
                statement.executeUpdate("DELETE FROM " + table);
                statement.executeUpdate("ALTER TABLE " + table + " ALTER COLUMN id RESTART WITH 1");
            }

        } catch (SQLException e) {
            LOG.error("Exception", e);
        }
    }
}
