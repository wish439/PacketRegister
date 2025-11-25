package com.wishtoday.packetregister.Util;

import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Method;
import java.util.Arrays;

@SuppressWarnings("ClassCanBeRecord")
@Log4j2
public class MethodGetter implements ReflectGetter<Method> {
    private final String className;
    private final String methodName;
    public MethodGetter(String className
            , String fieldName) {
        if (className.contains("/")) className = className.replace("/", ".");
        this.className = className;
        this.methodName = fieldName;
    }

    @Override
    public String className() {
        return className;
    }

    @Override
    public Method getValue(Class<?> clazz) {
        try {
            Method method = clazz.getDeclaredMethod(methodName);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("method " + methodName + " doesn't exist" + e.getMessage());
        }
    }

    public Method loadAndGetWithArgs(Class<?>... args) {
        Class<?> clazz;
        try {
            clazz = Class.forName(this.className());
            return clazz.getDeclaredMethod(this.methodName, args);
        } catch (ClassNotFoundException e) {
            log.error("class {} doesn't exist", this.className(), e);
            return null;
            //throw new IllegalArgumentException("class " + this.getClassName() + " doesn't exist" + e.getMessage());
        } catch (NoSuchMethodException e) {
            log.error("method {} args: {} doesn't exist", this.methodName, Arrays.toString(Arrays.stream(args).map(Class::getSimpleName).toArray()), e);
            return null;
            //throw new IllegalArgumentException("method " + this.methodName + " " + Arrays.toString(classes) + "doesn't exist" + e.getMessage());
        }
    }
}
