package store;

import model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountStore {
    private static final Logger LOG = LoggerFactory.getLogger(AccountStore.class.getName());

    private final ConnectionFactory connectionFactory;

    public AccountStore(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public Account findByEmail(String email) {
        Account rsl = null;
        try (Connection cn = connectionFactory.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM account where email = ?"
             )
        ) {
            ps.setString(1, email);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    rsl = new Account(
                            it.getString("username"),
                            it.getString("email"),
                            it.getString("phone"),
                            it.getInt("id")
                    );
                }
            }
        } catch (Exception e) {
            LOG.error("Exception", e);
        }

        return rsl;
    }

    public void save(Account account) {
        try (Connection cn = connectionFactory.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO account(username, email, phone) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )
        ) {
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getEmail());
            ps.setString(3, account.getPhone());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    account.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
    }
}
