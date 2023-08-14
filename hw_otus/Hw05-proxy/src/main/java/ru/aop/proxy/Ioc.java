package ru.aop.proxy;

import ru.aop.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

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
        private final List<Method> loggingMethods;

        TestLoggingInvocationHandler(TestLogging testLogging) {
            this.testLogging = testLogging;
            this.loggingMethods = Arrays.stream(testLogging
                            .getClass()
                            .getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(Log.class))
                    .toList();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            loggingMethods.stream()
                    .filter(m -> m.getName().equals(method.getName()))
                    .filter(m -> Arrays.equals(m.getParameterTypes(), method.getParameterTypes()))
                    .findAny()
                    .ifPresent(x -> System.out.printf("executed method: %s, params: %s\n",
                            method.getName(),
                            Arrays.toString(args)));
            return method.invoke(testLogging, args);
        }
    }
}
