package com.ecwid.deepcopy.deep_copy;

import java.lang.reflect.Array;
import java.util.IdentityHashMap;
import java.util.Objects;

public class ArrayCopyHandler implements CopyHandler {

    @Override
    public boolean supports(Object obj) {
        return Objects.nonNull(obj) && obj.getClass().isArray();
    }

    @Override
    public Object copy(Object obj, IdentityHashMap<Object, Object> context) {
        int length = Array.getLength(obj);
        Object copiedArray = Array.newInstance(obj.getClass().getComponentType(), length);
        context.put(obj, copiedArray);

        for (int i = 0; i < length; i++) {
            Object element = Array.get(obj, i);
            Object copiedElement = DeepCopy.deepCopyInternal(element, context);
            Array.set(copiedArray, i, copiedElement);
        }
        return copiedArray;
    }
}
