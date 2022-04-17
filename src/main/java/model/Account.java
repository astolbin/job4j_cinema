package model;

import java.util.Objects;

public class Account {
    private int id;
    private String username;
    private String phone;
    private String email;

    public Account(String username, String email, String phone) {
        this(username, email, phone, 0);
    }

    public Account(String username, String email, String phone, int id) {
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return id == account.id && Objects.equals(username, account.username)
                && Objects.equals(phone, account.phone) && Objects.equals(email, account.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, phone, email);
    }
}
