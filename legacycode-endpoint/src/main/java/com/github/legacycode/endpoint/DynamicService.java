package com.github.legacycode.endpoint;

import java.util.Optional;
import java.util.Set;
import jakarta.json.JsonObject;

public interface DynamicService<E, D> {

    PaginationData<D> onFilter(String name, Set<DynamicQuery> queries);

    Optional<D> onFind(String name, String id);

    <K> K onCreate(String name, JsonObject document);

    void onUpdate(String name, JsonObject document, String id);

    void onDelete(String name, String id);

}
