package ru.aop.proxy;

import ru.aop.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

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
        private final Map<String, List<Class<?>[]>> loggingMethods;

        TestLoggingInvocationHandler(TestLogging testLogging) {
            this.testLogging = testLogging;
            this.loggingMethods = new HashMap<>();

            for (var method : testLogging.getClass().getDeclaredMethods()) {
                if (!method.isAnnotationPresent(Log.class))
                    continue;
                var methodName = method.getName();
                loggingMethods.putIfAbsent(methodName, new ArrayList<>());
                loggingMethods.get(methodName).add(method.getParameterTypes());
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (methodPresent(loggingMethods, method))
                System.out.printf("executed method: %s, params: %s\n", method.getName(), Arrays.toString(args));
            return method.invoke(testLogging, args);
        }

        private boolean methodPresent(Map<String, List<Class<?>[]>> loggingMethods, Method method) {
            var isPresent = false;
            for (var parameters : loggingMethods.get(method.getName()))
                if (Arrays.equals(parameters, method.getParameterTypes())) {
                    isPresent = true;
                    break;
                }
            return isPresent;
        }
    }
}