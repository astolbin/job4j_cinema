package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import helper.TestTools;
import model.Account;
import model.Ticket;
import org.junit.Before;
import org.junit.Test;
import service.CinemaCashBox;
import store.DbConnectionFactory;
import store.TicketStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HallServletTest {

    @Before
    public void setUp() {
        TestTools.clearTables();
    }

    @Test
    public void testWhenSessionWithTickets() throws IOException {
        Gson gson = new GsonBuilder().create();
        TicketStore ticketStore = new TicketStore(DbConnectionFactory.instOf());

        Account account = new Account("test", "test@mail.com", "123123123");
        Ticket ticket = new Ticket(1, 1, 1, account.getId());

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        CustomOutputStream outputStream = new CustomOutputStream();
        when(req.getParameter("session_id")).thenReturn("1");
        when(resp.getOutputStream()).thenReturn(outputStream);

        CinemaCashBox.buyTicket(account, ticket);
        List<Ticket> tickets = ticketStore.getBySession(1);

        new HallServlet().doGet(req, resp);

        assertEquals(gson.toJson(tickets), outputStream.getContentAsString());
    }
}