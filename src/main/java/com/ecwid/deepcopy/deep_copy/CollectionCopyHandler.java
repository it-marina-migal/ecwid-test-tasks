package com.ecwid.deepcopy.deep_copy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import static com.ecwid.deepcopy.deep_copy.Constant.Errors.FAILED_COPY_COLLECTION;
import static com.ecwid.deepcopy.deep_copy.Constant.Errors.UNSUPPORTED_COLLECTION_TYPE;

public class CollectionCopyHandler implements CopyHandler {

    @Override
    public boolean supports(Object obj) {
        return obj instanceof Collection<?>;
    }

    @Override
    public Object copy(Object obj, IdentityHashMap<Object, Object> context) {
        Collection<?> original = (Collection<?>) obj;
        try {
            Collection<Object> clone = createEmptyCollectionInstance(original);
            context.put(obj, clone);
            for (Object element : original) {
                clone.add(DeepCopy.deepCopyInternal(element, context));
            }
            return clone;
        } catch (Exception e) {
            throw new DeepCopyException(FAILED_COPY_COLLECTION + obj.getClass(), e);
        }
    }

    private static Collection<Object> createEmptyCollectionInstance(Collection<?> collection) {
        return switch (collection) {
            case Deque<?> ignored -> new LinkedList<>();
            case Queue<?> ignored -> new LinkedList<>();
            case List<?> ignored -> new ArrayList<>();
            case Set<?> ignored -> new HashSet<>();
            default -> throw new UnsupportedOperationException(
                    UNSUPPORTED_COLLECTION_TYPE + collection.getClass().getName());
        };
    }
}
