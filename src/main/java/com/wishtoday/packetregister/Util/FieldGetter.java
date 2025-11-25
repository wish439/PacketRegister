package com.wishtoday.packetregister.Util;

import java.lang.reflect.Field;

public record FieldGetter(String className, String fieldName) implements ReflectGetter<Field> {
    public FieldGetter {
        if (className.contains("/")) className = className.replace("/", ".");
    }

    @Override
    public Field getValue(Class<?> clazz) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("field " + fieldName + " doesn't exist" + e.getMessage());
        }
    }

    public Object getStaticField() {
        try {
            return loadAndGet().get(null);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("on FieldGetter$$getStaticField threw " + e.getMessage());
        }
    }
}