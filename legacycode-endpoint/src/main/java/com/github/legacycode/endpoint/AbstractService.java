package com.github.legacycode.endpoint;

import java.util.Optional;
import java.util.Set;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.json.JsonObject;

public abstract class AbstractService<E, D, M extends DynamicMapper<E, D>> implements DynamicService<E, D> {

    protected final EntryManager entryManager;
    protected final DynamicDAO dao;

    protected AbstractService(final EntryManager entryManager, final DynamicDAO dao) {
        this.entryManager = entryManager;
        this.dao = dao;
    }

    @Override
    public PaginationData<D> onFilter(final String name, final Set<DynamicQuery> queries) {

        var entry = this.entryManager.<E, D, M, AbstractService<E, D, M>>resolve(name);
        if (!entry.actions().contains(Action.FILTER)) {
            throw new ActionException(Action.FILTER);
        }

        var entityClass = entry.entityClass();
        var mapperClass = entry.mapper();
        var mapper = CDI.current().select(mapperClass).get();

        var entities = this.dao.find(entityClass, queries);
        var size = this.dao.size(entityClass, queries);
        var pageSize = Queries.getPageSize(queries);
        var pageNumber = Queries.getPageNumber(queries);
        var pageCount = Queries.getPageCount(queries, size);

        var data = mapper.toData(entities);

        var paginationData = new PaginationData<D>();
        paginationData.setData(data);
        paginationData.setSize(size);
        paginationData.setPageSize(pageSize);
        paginationData.setPageNumber(pageNumber);
        paginationData.setPageCount(pageCount);
        return paginationData;

    }

    @Override
    public Optional<D> onFind(final String name, final String id) {

        var entry = this.entryManager.<E, D, M, AbstractService<E, D, M>>resolve(name);
        if (!entry.actions().contains(Action.FIND)) {
            throw new ActionException(Action.FIND);
        }

        var entityClass = entry.entityClass();
        var mapperClass = entry.mapper();
        var mapper = CDI.current().select(mapperClass).get();

        return this.dao
                .find(entityClass, id)
                .map(mapper::toData);
    }

    @Override
    public <K> K onCreate(final String name, final JsonObject document) {

        var entry = this.entryManager.<E, D, M, AbstractService<E, D, M>>resolve(name);
        if (!entry.actions().contains(Action.CREATE)) {
            throw new ActionException(Action.CREATE);
        }


        var dataClass = entry.dataClass();
        var mapperClass = entry.mapper();
        var mapper = CDI.current().select(mapperClass).get();

        var data = Jsons.parse(dataClass, document);
        var entity = mapper.toEntity(data);

        if (this.dao.contains(entity)) {
            throw new EndpointException("Entity already exist !");
        }

        entity = this.dao.save(entity);
        return this.dao.getPrimaryKey(entity);

    }

    @Override
    public void onUpdate(final String name, final JsonObject document, final String id) {

        var entry = this.entryManager.<E, D, M, AbstractService<E, D, M>>resolve(name);
        if (!entry.actions().contains(Action.UPDATE)) {
            throw new ActionException(Action.UPDATE);
        }

        var entityClass = entry.entityClass();
        var dataClass = entry.dataClass();
        var mapperClass = entry.mapper();
        var mapper = CDI.current().select(mapperClass).get();

        var data = Jsons.parse(dataClass, document);
        var entity = this.dao
                .find(entityClass, id)
                .orElseThrow(() -> new EndpointException("Entity not exist !"));

        mapper.updateFromData(data, entity);
        this.dao.save(entity);
    }

    @Override
    public void onDelete(final String name, final String id) {

    }
}
