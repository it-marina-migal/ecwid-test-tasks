package com.ecwid.deepcopy.deep_copy;

import java.util.IdentityHashMap;
import java.util.UUID;

public class UuidCopyHandler implements CopyHandler {

    @Override
    public boolean supports(Object obj) {
        return obj instanceof UUID;
    }

    @Override
    public Object copy(Object obj, IdentityHashMap<Object, Object> context) {
        return obj;
    }
}
