package com.wishtoday.packetregister.Util;

public interface ReflectGetter<R> {
    String className();
    R getValue(Class<?> clazz);
    default R loadAndGet() {
        Class<?> clazz;
        try {
            clazz = Class.forName(this.className());
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("class " + this.className() + " doesn't exist" + e.getMessage());
        }
        return getValue(clazz);
    }
}
