package ru.oop.atm;

public enum Denominations {
    TEN (10),
    FIFTY (50),
    HUNDRED (100);

    private final int denomination;

    Denominations(int denomination) {
        this.denomination = denomination;
    }

    public int getDenomination(){
        return denomination;
    }
}
