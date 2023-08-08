package ru.aop.proxy;

import ru.aop.annotations.Log;

public class TestLoggingImpl implements TestLogging {
    @Log
    @Override
    public void calculation(int param) {
    }

    @Log
    @Override
    public void calculation(int param1, String param2) {
    }

    @Override
    public void test(int param1, int param2) {
    }
}
