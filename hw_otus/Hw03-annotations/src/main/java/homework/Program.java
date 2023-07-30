package homework;

import homework.test.BankAccountTest;

public class Program {
    public static void main(String[] args) throws Exception{
        var launcher = new Launcher();
        launcher.launch(BankAccountTest.class);
    }
}
