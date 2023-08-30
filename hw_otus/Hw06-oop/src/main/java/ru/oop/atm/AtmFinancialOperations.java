package ru.oop.atm;

public interface AtmFinancialOperations {
    void putCash(Denominations denomination,int banknotesAmount);
    void getCash(int amount);
    void displayBalance();
}