package com.ecwid.deepcopy.deep_copy;

import java.util.IdentityHashMap;
import java.util.Optional;

public class OptionalCopyHandler implements CopyHandler {

    @Override
    public boolean supports(Object obj) {
        return obj instanceof Optional<?>;
    }

    @Override
    public Object copy(Object obj, IdentityHashMap<Object, Object> context) {
        Optional<?> original = (Optional<?>) obj;
        return original.map(value -> DeepCopy.deepCopyInternal(value, context));
    }
}
