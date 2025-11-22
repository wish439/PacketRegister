package com.wishtoday.packetregister.Util;

public interface ReflectGetter<R> {
    String getClassName();
    String getName();
    R getValue(Class<?> clazz);
    default R loadAndGet() {
        Class<?> clazz;
        try {
            clazz = Class.forName(this.getClassName());
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("class " + this.getClassName() + " doesn't exist" + e.getMessage());
        }
        return getValue(clazz);
    }
}
