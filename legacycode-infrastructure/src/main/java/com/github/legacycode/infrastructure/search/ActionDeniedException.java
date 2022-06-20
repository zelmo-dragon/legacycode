package com.github.legacycode.infrastructure.search;

import java.io.Serial;

public final class ActionDeniedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Action action;

    public ActionDeniedException(final Action action) {
        super(String.format("Action type [%s] denied !", action));
        this.action = action;
    }

    public Action getAction() {
        return action;
    }
}
