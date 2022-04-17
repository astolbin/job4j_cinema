package store;

import helper.TestTools;
import model.Account;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountStoreTest {

    @Before
    public void setUp() {
        TestTools.clearTables();
    }

    @Test
    public void whenSaveAndFoundAccount() {
        ConnectionFactory connectionFactory = DbConnectionFactory.instOf();
        AccountStore accountStore = new AccountStore(connectionFactory);
        Account account = new Account("test", "test@mail.com", "123123123");

        accountStore.save(account);

        assertEquals(account, accountStore.findByEmail("test@mail.com"));
    }

    @Test
    public void whenNotFoundAccount() {
        ConnectionFactory connection = DbConnectionFactory.instOf();
        AccountStore accountStore = new AccountStore(connection);

        assertNull(accountStore.findByEmail("test@mail.com"));
    }
}