package homework.test;

import homework.BankAccount;
import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

public class BankAccountTest {

    BankAccount bankAccount;

    @Before
    public void setUp() throws Exception {
        bankAccount = new BankAccount(1000, "EUR");
    }

    @Test
    public void correctAddMoneyTest() throws Exception {
        bankAccount.addMoney(500);
        var actual = bankAccount.getAccountBalance();
        var expected = 1500;
        if (expected != actual)
            throw new Exception();
    }

    @Test
    public void incorrectAddMoneyTest() throws Exception {
        bankAccount.addMoney(500);
        var actual = bankAccount.getAccountBalance();
        var expected = 5;
        if (expected != actual)
            throw new Exception();
    }

    @Test
    public void correctConversionTest() throws Exception {
        var expected = new BankAccount(50000, "RUB");
        bankAccount.convertToRub(50);
        if (!expected.equals(bankAccount))
            throw new Exception();
    }

    @After
    public void after() throws Exception {
        if(bankAccount == null)
            throw new Exception();
    }
}
