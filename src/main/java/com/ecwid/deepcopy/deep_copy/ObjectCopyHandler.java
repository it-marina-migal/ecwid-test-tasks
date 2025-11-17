package com.ecwid.deepcopy.deep_copy;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;

import static com.ecwid.deepcopy.deep_copy.Constant.Errors.FAILED_COPY_OBJECT_FIELDS;

public class ObjectCopyHandler implements CopyHandler {

    @Override
    public boolean supports(Object obj) {
        return true;
    }

    @Override
    public Object copy(Object obj, IdentityHashMap<Object, Object> context) {
        Class<?> clazz = obj.getClass();
        Object clone = InstanceFactory.create(clazz);
        context.put(obj, clone);

        try {
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(clazz, MethodHandles.lookup());
            while (clazz != null) {
                for (Field field : clazz.getDeclaredFields()) {
                    if (Modifier.isStatic(field.getModifiers())) continue;

                    field.setAccessible(true);
                    VarHandle handle = lookup.findVarHandle(clazz, field.getName(), field.getType());
                    Object value = DeepCopy.deepCopyInternal(handle.get(obj), context);
                    handle.set(clone, value);
                }
                clazz = clazz.getSuperclass();
            }
            return clone;
        } catch (Throwable e) {
            throw new DeepCopyException(FAILED_COPY_OBJECT_FIELDS, e);
        }
    }
}
