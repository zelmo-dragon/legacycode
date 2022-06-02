package com.github.legacycode.endpoint;

import java.net.URI;
import java.util.Set;
import jakarta.json.JsonObject;

public abstract class AbstractService<E, D> implements DynamicService<E, D> {

    protected final EntryManager entryManager;
    protected final DynamicDAO dao;

    protected AbstractService(final EntryManager entryManager, final DynamicDAO dao) {
        this.entryManager = entryManager;
        this.dao = dao;
    }

    @Override
    public PaginationData<D> onFilter(final String name, final Set<DynamicQuery> queries) {

        return null;
    }

    @Override
    public D onFind(final String name, final String id) {
        return null;
    }

    @Override
    public URI onCreate(final String name, final JsonObject data) {
        return null;
    }

    @Override
    public void onUpdate(final String name, final JsonObject data, final String id) {

    }

    @Override
    public void onDelete(final String name, final String id) {

    }
}
