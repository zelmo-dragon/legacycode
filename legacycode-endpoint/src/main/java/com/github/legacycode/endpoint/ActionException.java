package com.github.legacycode.endpoint;

import java.io.Serial;

public class ActionException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Action action;

    public ActionException(final Action action) {
        super();
        this.action = action;
    }

    public Action getAction() {
        return action;
    }
}
