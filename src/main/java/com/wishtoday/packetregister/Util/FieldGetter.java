package com.wishtoday.packetregister.Util;

import lombok.Getter;

import java.lang.reflect.Field;

@Getter
public class FieldGetter implements ReflectGetter<Field>{
    private final String className;
    private final String fieldName;
    public FieldGetter(String className
            , String fieldName) {
        if (className.contains("/")) className = className.replace("/", ".");
        this.className = className;
        this.fieldName = fieldName;
    }
    public FieldGetter(Field field) {
        this.className = field.getType().getName();
        this.fieldName = field.getName();
    }

    @Override
    public String getName() {
        return fieldName;
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