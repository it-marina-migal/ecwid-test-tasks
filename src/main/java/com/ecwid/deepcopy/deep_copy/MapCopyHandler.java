package com.ecwid.deepcopy.deep_copy;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import static com.ecwid.deepcopy.deep_copy.Constant.Errors.FAILED_COPY_MAP;
import static com.ecwid.deepcopy.deep_copy.Constant.Errors.UNSUPPORTED_MAP_TYPE;

public class MapCopyHandler implements CopyHandler {

    @Override
    public boolean supports(Object obj) {
        return obj instanceof Map<?, ?>;
    }

    @Override
    public Object copy(Object obj, IdentityHashMap<Object, Object> context) {
        Map<?, ?> original = (Map<?, ?>) obj;
        try {
            Map<Object, Object> clone = createEmptyMapInstance(original);
            context.put(obj, clone);
            for (Map.Entry<?, ?> entry : original.entrySet()) {
                Object copiedKey = DeepCopy.deepCopyInternal(entry.getKey(), context);
                Object copiedValue = DeepCopy.deepCopyInternal(entry.getValue(), context);
                clone.put(copiedKey, copiedValue);
            }
            return clone;
        } catch (Exception e) {
            throw new DeepCopyException(FAILED_COPY_MAP + obj.getClass(), e);
        }
    }

    private static Map<Object, Object> createEmptyMapInstance(Map<?, ?> map) {
        return switch (map) {
            case LinkedHashMap<?, ?> ignored -> new LinkedHashMap<>();
            case HashMap<?, ?> ignored -> new HashMap<>();
            case TreeMap<?, ?> ignored -> new TreeMap<>();
            default -> throw new UnsupportedOperationException(
                    UNSUPPORTED_MAP_TYPE + map.getClass().getName());
        };
    }
}
