package ru.oop.atm;

public interface AtmFinancialOperations {
    void putCash(Denominations denomination,int banknotesAmount);
    int getCash(int amount);
    void displayBalance();
}