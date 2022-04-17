package service;

import model.Account;
import model.Ticket;
import store.AccountStore;
import store.ConnectionFactory;
import store.DbConnectionFactory;
import store.TicketStore;

public class CinemaCashBox {

    public static BuyTicketResult buyTicket(Account account, Ticket ticket) {
        BuyTicketResult buyResult;
        try {
            ConnectionFactory connection = DbConnectionFactory.instOf();
            AccountStore accountStore = new AccountStore(connection);
            TicketStore ticketStore = new TicketStore(connection);

            Account accountFound = accountStore.findByEmail(account.getEmail());
            if (accountFound == null) {
                accountStore.save(account);
            } else {
                account = accountFound;
            }
            ticket.setAccount(account.getId());
            ticketStore.save(ticket);
            buyResult = new BuyTicketResult(true, "Билет оплачен.");
        } catch (Exception e) {
            buyResult = new BuyTicketResult(
                    false,
                    "Ошибка оплаты. Попробуйте выбрать другое место."
            );
        }

        return buyResult;
    }

    public static class BuyTicketResult {
        private final boolean isSuccess;
        private String message;

        public BuyTicketResult(boolean isSuccess, String message) {
            this.isSuccess = isSuccess;
            this.message = message;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
