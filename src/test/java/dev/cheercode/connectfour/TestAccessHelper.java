package dev.cheercode.connectfour;

import java.lang.reflect.Method;

public class TestAccessHelper {
    public static <T> T callPrivateMethod(
            Object target,
            String methodName,
            Class<?>[] parameterTypes,
            Object... args
    ) throws Exception {
        Method method = target.getClass().getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return (T) method.invoke(target, args);
    }
}
