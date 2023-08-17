package ru.aop.proxy;

import ru.aop.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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
        private final Map<String, Object[]> loggingMethods;

        TestLoggingInvocationHandler(TestLogging testLogging) {
            this.testLogging = testLogging;
            this.loggingMethods = Arrays.stream(testLogging.getClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(Log.class))
                    .collect(Collectors.toMap(
                            Method::getName,
                            Method::getParameterTypes));
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            var methodName = method.getName();
            if (loggingMethods.containsKey(methodName)
                    && Arrays.equals(loggingMethods.get(methodName), method.getParameterTypes()))
                System.out.printf("executed method: %s, params: %s\n", methodName, Arrays.toString(args));
            return method.invoke(testLogging, args);
        }
    }
}