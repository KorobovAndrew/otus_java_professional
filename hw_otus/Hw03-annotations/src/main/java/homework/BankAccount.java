package homework;

import java.util.Objects;

public class BankAccount {

    private int accountBalance;
    private String currency;

    public BankAccount(int money, String currency) {
        this.accountBalance = money;
        this.currency = currency;
    }

    public int getAccountBalance() {
        return accountBalance;
    }

    public void addMoney(int amount){
        this.accountBalance += amount;
    }

    public void convertToRub(double rate){
        accountBalance *= rate;
        currency = "RUB";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return accountBalance == that.accountBalance && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountBalance, currency);
    }
}
