package ru.oop;

import ru.oop.atm.Atm;
import ru.oop.atm.Denominations;
import ru.oop.outputService.ConsolePrinter;

public class App {
    public static void main(String[] args) {
        //10 x 1, 50 x 1, 100 x 1, sum = 160
        var atm = new Atm(new ConsolePrinter());
        atm.displayBalance();
        System.out.println("____________________________");

        //10 x 2, 50 x 1, 100 x 1, sum = 170
        atm.putCash(Denominations.TEN, 1);
        atm.displayBalance();
        System.out.println("____________________________");

        //unable to pay the requested amount
        //10 x 2, 50 x 1, 100 x 1, sum = 170
        try {
            atm.getCash(1000);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());;
        }
        atm.displayBalance();
        System.out.println("____________________________");

        //unable to pay the requested amount
        //10 x 2, 50 x 1, 100 x 1, sum = 170
        try {
            atm.getCash(5);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());;
        }
        atm.displayBalance();
        System.out.println("____________________________");

        //unable to pay the requested amount
        //10 x 2, 50 x 1, 100 x 1, sum = 170
        try {
            atm.getCash(30);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());;
        }
        atm.displayBalance();
        System.out.println("____________________________");

        //10 x 1, 50 x 1, 100 x 0, sum = 60
        try {
            atm.getCash(110);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());;
        }
        atm.displayBalance();
        System.out.println("____________________________");

    }
}
