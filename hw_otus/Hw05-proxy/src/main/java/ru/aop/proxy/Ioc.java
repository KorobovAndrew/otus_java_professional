package ru.aop.proxy;

import ru.aop.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Ioc {

    private Ioc() {
    }

    public static TestLogging createTestLogging() {
        InvocationHandler handler = new TestLoggingInvocationHandler(new TestLoggingImpl());
        return (TestLogging)
                Proxy.newProxyInstance(
                        Ioc.class.getClassLoader(),
                        new Class<?>[]{TestLogging.class},
                        handler);
    }

    static class TestLoggingInvocationHandler implements InvocationHandler {
        private final TestLogging testLogging;

        TestLoggingInvocationHandler(TestLogging testLogging) {
            this.testLogging = testLogging;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            var m = method.getName();
            if (testLogging
                    .getClass()
                    .getDeclaredMethod(method.getName(), method.getParameterTypes())
                    .isAnnotationPresent(Log.class))
                System.out.printf("executed method: %s, params: %s\n", method.getName(), Arrays.toString(args));
            return method.invoke(testLogging, args);
        }
    }
}
