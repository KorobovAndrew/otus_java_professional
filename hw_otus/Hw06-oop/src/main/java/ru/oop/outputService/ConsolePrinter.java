package ru.oop.outputService;

public class ConsolePrinter implements OutputService {
    @Override
    public void print(int amount) {
        System.out.println(amount);
    }
}
