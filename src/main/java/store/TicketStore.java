package store;

import model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TicketStore {
    private static final Logger LOG = LoggerFactory.getLogger(TicketStore.class.getName());

    private final ConnectionFactory connectionFactory;

    public TicketStore(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public List<Ticket> getBySession(int sessionId) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = connectionFactory.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM ticket where session_id = ?"
             )
        ) {
            ps.setInt(1, sessionId);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(
                            new Ticket(
                                    it.getInt("line"),
                                    it.getInt("cell"),
                                    it.getInt("session_id"),
                                    it.getInt("account_id"),
                                    it.getInt("id")
                            )
                    );
                }
            }
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
        return tickets;
    }

    public void save(Ticket ticket) {
        try (Connection cn = connectionFactory.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO ticket(session_id, line, cell, account_id) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )
        ) {
            ps.setInt(1, ticket.getSession());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getAccount());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception", e);
            throw new IllegalStateException();
        }
    }
}
