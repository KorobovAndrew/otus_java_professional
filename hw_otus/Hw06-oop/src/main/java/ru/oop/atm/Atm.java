package ru.oop.atm;

import ru.oop.outputService.OutputService;

import java.util.Comparator;
import java.util.HashMap;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Atm implements AtmFinancialOperations {
    private final int DEFAULT_BANKNOTES_AMOUNT = 1;
    private final NavigableMap<Denominations, Integer> banknotesAmount;
    private final OutputService printer;

    public Atm(OutputService printer) {
        this.printer = printer;
        this.banknotesAmount = new TreeMap<>(Comparator.comparing(Denominations::getDenomination));
        for (var denomination : Denominations.values()) {
            banknotesAmount.put(denomination, DEFAULT_BANKNOTES_AMOUNT);
        }
    }

    @Override
    public void putCash(Denominations denomination, int banknotesAmount) {
        this.banknotesAmount.compute(denomination, (k, v) -> v + banknotesAmount);
    }

    @Override
    public int getCash(int amount) throws RuntimeException {
        var givingBanknotes = new HashMap<Denominations, Integer>();
        for (var denomination : Denominations.values()) {
            givingBanknotes.put(denomination, 0);
        }
        var rest = amount;

        for (var availableBanknotes : banknotesAmount.descendingMap().entrySet()) {
            var denomination = availableBanknotes.getKey();
            var atmBanknotesCount = availableBanknotes.getValue();
            while (rest >= denomination.getDenomination()
                    && atmBanknotesCount > givingBanknotes.get(denomination)) {
                givingBanknotes.compute(denomination, (k, v) -> ++v);
                rest -= denomination.getDenomination();
            }
        }

        if (rest == 0) {
            for (var entry : givingBanknotes.entrySet()) {
                banknotesAmount.compute(entry.getKey(), (k, v) -> v - entry.getValue());
            }
        } else {
            throw new RuntimeException("unable to pay the requested amount");
        }
        return amount;
    }

    @Override
    public void displayBalance() {
        printer.print(getBalance());
    }

    public int getBalance() {
        return banknotesAmount.entrySet().stream()
                .map(entry -> entry.getKey().getDenomination() * entry.getValue())
                .reduce(Integer::sum)
                .orElseThrow(RuntimeException::new);
    }
}