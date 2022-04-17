package store;

import helper.TestTools;
import model.Account;
import model.Ticket;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TicketStoreTest {

    @Before
    public void setUp() {
        TestTools.clearTables();
    }

    @Test
    public void whenSaveAndFoundTicket() {
        ConnectionFactory connectionFactory = DbConnectionFactory.instOf();
        TicketStore ticketStore = new TicketStore(connectionFactory);
        AccountStore accountStore = new AccountStore(connectionFactory);
        Account account = new Account("test", "test@mail.com", "1212312");
        Ticket ticket = new Ticket(1, 1, 1, 0);

        accountStore.save(account);
        ticket.setAccount(account.getId());
        ticketStore.save(ticket);

        Ticket rsl = ticketStore.getBySession(1).get(0);

        assertEquals(ticket, rsl);
    }

    @Test(expected = IllegalStateException.class)
    public void whenSaveDuplicateTicket() {
        ConnectionFactory connectionFactory = DbConnectionFactory.instOf();
        TicketStore ticketStore = new TicketStore(connectionFactory);
        AccountStore accountStore = new AccountStore(connectionFactory);
        Account account = new Account("test", "test@mail.com", "1212312");
        Ticket ticket = new Ticket(1, 1, 1, 0);

        accountStore.save(account);
        ticket.setAccount(account.getId());
        ticketStore.save(ticket);
        ticketStore.save(ticket);
    }
}