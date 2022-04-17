package model;

import java.util.Objects;

public class Ticket {
    private int id;
    private int row;
    private int cell;
    private int account;
    private int session;

    public Ticket(int row, int cell, int session, int account) {
        this.row = row;
        this.cell = cell;
        this.session = session;
        this.account = account;
    }

    public Ticket(int row, int cell, int session, int account, int id) {
        this(row, cell, session, account);
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return id == ticket.id && row == ticket.row && cell == ticket.cell
                && account == ticket.account && session == ticket.session;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, row, cell, account, session);
    }
}
