package com.ecwid.deepcopy.deep_copy;

import java.util.IdentityHashMap;
import java.util.Objects;

public class ImmutableCopyHandler implements CopyHandler {

    @Override
    public boolean supports(Object obj) {
        if (Objects.isNull(obj)) return false;

        Class<?> clazz = obj.getClass();
        return ImmutableTypes.isImmutable(clazz) || clazz.isEnum();
    }

    @Override
    public Object copy(Object obj, IdentityHashMap<Object, Object> context) {
        return obj;
    }
}
