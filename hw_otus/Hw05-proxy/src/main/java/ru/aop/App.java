package ru.aop;

import ru.aop.proxy.Ioc;
import ru.aop.proxy.TestLogging;

public class App {
    public static void main(String[] args) {
        TestLogging testLogging = Ioc.createTestLogging();
        testLogging.calculation(6);
        testLogging.calculation(6, "qqq");
        testLogging.test(6, 1);
    }
}
