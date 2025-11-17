package com.ecwid.deepcopy.deep_copy;

import java.util.IdentityHashMap;
import java.util.List;

import static com.ecwid.deepcopy.deep_copy.Constant.Errors.UNSUPPORTED_OBJECT_TYPE;

public class DeepCopy {

    private static final List<CopyHandler> COPY_HANDLERS = List.of(
            new ImmutableCopyHandler(),
            new OptionalCopyHandler(),
            new UuidCopyHandler(),
            new ArrayCopyHandler(),
            new CollectionCopyHandler(),
            new MapCopyHandler(),
            new ObjectCopyHandler()
    );

    public static <T> T copy(T originalObject) {
        return (T) deepCopyInternal(originalObject, new IdentityHashMap<>());
    }

    static Object deepCopyInternal(Object originalObject, IdentityHashMap<Object, Object> context) {
        if (originalObject == null) return null;

        if (context.containsKey(originalObject)) return context.get(originalObject);

        for (CopyHandler handler : COPY_HANDLERS) {
            if (handler.supports(originalObject)) {
                Object result = handler.copy(originalObject, context);
                context.put(originalObject, result);
                return result;
            }
        }

        throw new DeepCopyException(UNSUPPORTED_OBJECT_TYPE + originalObject.getClass());
    }
}
