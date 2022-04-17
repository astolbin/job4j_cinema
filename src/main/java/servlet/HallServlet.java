package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Ticket;
import store.DbConnectionFactory;
import store.TicketStore;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@WebServlet(urlPatterns = {"/hall-tickets"})
public class HallServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");

        int sessionId = Integer.parseInt(req.getParameter("session_id"));
        TicketStore ticketRepository = new TicketStore(DbConnectionFactory.instOf());
        List<Ticket> tickets = ticketRepository.getBySession(sessionId);

        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(tickets);
        output.write(json.getBytes());
        output.flush();
        output.close();
    }
}
