package com.wishtoday.packetregister.Util;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.Nullable;

@Log4j2
public class ClassUtil {
    private ClassUtil() {}
    @Nullable
    public static Class<?> getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.warn("{} not found{}", className, e.getMessage());
            return null;
            //throw new IllegalArgumentException(className + " not found" + e.getMessage());
        }
    }
}
