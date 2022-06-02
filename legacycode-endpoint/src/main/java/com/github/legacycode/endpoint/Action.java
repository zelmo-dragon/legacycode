package com.github.legacycode.endpoint;

import java.util.Set;

public enum Action {

    FILTER,
    FIND,
    CREATE,
    UPDATE,
    DELETE;

    public static final Set<Action> ALL = Set.of(Action.values());

    public static final Set<Action> READ_ONLY = Set.of(FILTER, FIND);
}
