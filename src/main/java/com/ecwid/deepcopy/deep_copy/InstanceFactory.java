package com.ecwid.deepcopy.deep_copy;

import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;

import static com.ecwid.deepcopy.deep_copy.Constant.Errors.FAILED_CREATE_CLASS_INSTANCE;

public final class InstanceFactory {

    private static final ReflectionFactory REFLECTION_FACTORY = ReflectionFactory.getReflectionFactory();

    private InstanceFactory() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> clazz) {
        try {
            Constructor<?> declaredConstructor = Object.class.getDeclaredConstructor();
            Constructor<?> newConstructor =
                    REFLECTION_FACTORY.newConstructorForSerialization(clazz, declaredConstructor);
            newConstructor.setAccessible(true);
            return (T) newConstructor.newInstance();
        } catch (Exception e) {
            throw new DeepCopyException(FAILED_CREATE_CLASS_INSTANCE, e);
        }
    }
}