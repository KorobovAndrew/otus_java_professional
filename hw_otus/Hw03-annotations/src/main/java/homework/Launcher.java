package homework;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Launcher {
    public void launch(Class<?> testClass) throws Exception {

        var annotatedMethods = getAnnotatedMethods(testClass);
        var constructor = testClass.getConstructor();
        Map<String, Integer> testStatistics = new HashMap<>(Map.of("Tests count", 0,
                "Passed tests", 0,
                "Failed tests", 0));

        for (var testMethod : annotatedMethods.get("Test")) {
            var object = constructor.newInstance();
            try {
                testStatistics.compute("Tests count", (k, v) -> ++v);
                runTest(testMethod, annotatedMethods, object);
                testStatistics.compute("Passed tests", (k, v) -> ++v);
            } catch (Exception e) {
                testStatistics.compute("Failed tests", (k, v) -> ++v);
            }
        }

        testStatistics.forEach((k, v) -> System.out.printf("%s :\t%s\n", k, v));
    }

    private Map<String, List<Method>> getAnnotatedMethods(Class<?> clazz) {

        var methods = clazz.getDeclaredMethods();

        Map<String, List<Method>> annotatedMethods = new HashMap<>();

        for (var method : methods) {
            for (var annotation : method.getDeclaredAnnotations()) {
                var annotationName = annotation.annotationType().getSimpleName();
                annotatedMethods.putIfAbsent(annotationName, new ArrayList<>());
                annotatedMethods.get(annotationName).add(method);
            }
        }
        return annotatedMethods;
    }

    private void runTest(Method testMethod,
                         Map<String, List<Method>> annotatedMethods,
                         Object object) throws Exception {

        for (var beforeMethod : annotatedMethods.get("Before"))
            beforeMethod.invoke(object);

        testMethod.invoke(object);

        for (var afterMethod : annotatedMethods.get("After"))
            afterMethod.invoke(object);
    }
}
