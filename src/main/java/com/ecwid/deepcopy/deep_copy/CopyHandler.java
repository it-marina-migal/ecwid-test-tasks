package com.ecwid.deepcopy.deep_copy;

import java.util.IdentityHashMap;

public interface CopyHandler {

    boolean supports(Object obj);
    Object copy(Object obj, IdentityHashMap<Object, Object> context);
}
