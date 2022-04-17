package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Account;
import model.Ticket;
import service.CinemaCashBox;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(urlPatterns = {"/ticket"})
public class TicketServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("application/json; charset=utf-8");

        Ticket ticket = GSON.fromJson(req.getParameter("ticket"), Ticket.class);
        Account account = GSON.fromJson(req.getParameter("account"), Account.class);

        CinemaCashBox.BuyTicketResult buyResult = CinemaCashBox.buyTicket(account, ticket);

        OutputStream output = resp.getOutputStream();
        output.write(GSON.toJson(buyResult).getBytes());

        output.flush();
        output.close();
    }
}
