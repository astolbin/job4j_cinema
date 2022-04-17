package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import helper.TestTools;
import model.Account;
import model.Ticket;
import org.junit.Before;
import org.junit.Test;
import service.CinemaCashBox;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TicketServletTest {

    @Before
    public void setUp() {
        TestTools.clearTables();
    }

    @Test
    public void whenBuyTicketSuccess() throws IOException {
        Gson gson = new GsonBuilder().create();
        Account account = new Account("test", "test@mail.com", "123123123");
        Ticket ticket = new Ticket(1, 1, 1, 0);
        CinemaCashBox.BuyTicketResult buyResult =
                new CinemaCashBox.BuyTicketResult(true, "Билет оплачен.");

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        CustomOutputStream outputStream = new CustomOutputStream();
        when(req.getParameter("ticket")).thenReturn(gson.toJson(ticket));
        when(req.getParameter("account")).thenReturn(gson.toJson(account));
        when(resp.getOutputStream()).thenReturn(outputStream);

        new TicketServlet().doPost(req, resp);

        assertEquals(gson.toJson(buyResult), outputStream.getContentAsString());
    }

    @Test
    public void whenBuyTicketDuplicate() throws IOException {
        Gson gson = new GsonBuilder().create();
        Account account = new Account("test", "test@mail.com", "123123123");
        Ticket ticket = new Ticket(1, 1, 1, 0);
        CinemaCashBox.BuyTicketResult buyResult = new CinemaCashBox.BuyTicketResult(
                false,
                "Ошибка оплаты. Попробуйте выбрать другое место."
        );

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        CustomOutputStream buyOutput = new CustomOutputStream();
        when(resp.getOutputStream()).thenReturn(buyOutput);
        when(req.getParameter("ticket")).thenReturn(gson.toJson(ticket));
        when(req.getParameter("account")).thenReturn(gson.toJson(account));

        CinemaCashBox.buyTicket(account, ticket);
        new TicketServlet().doPost(req, resp);

        assertEquals(gson.toJson(buyResult), buyOutput.getContentAsString());
    }
}