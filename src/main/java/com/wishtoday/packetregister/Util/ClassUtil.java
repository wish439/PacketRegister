package com.wishtoday.packetregister.Util;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Log4j2
public class ClassUtil {
    private ClassUtil() {}
    @Nullable
    public static Class<?> getClass(@NotNull String className) {
        if (className.isEmpty()) {
            log.warn("{} is empty", className);
            return null;
        }
        if (className.startsWith("L") && className.endsWith(";")) className = className.substring(1, className.length() - 1);
        if (className.contains("/")) className = className.replace("/", ".");
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.warn("{} not found{}", className, e.getMessage());
            return null;
            //throw new IllegalArgumentException(className + " not found" + e.getMessage());
        }
    }
}
