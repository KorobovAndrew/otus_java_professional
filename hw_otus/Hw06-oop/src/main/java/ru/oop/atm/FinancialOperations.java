package ru.oop.atm;

public interface FinancialOperations {
    void putCash(Denominations denomination,int banknotesAmount);
    void getCash(int amount);
    void displayBalance();
}