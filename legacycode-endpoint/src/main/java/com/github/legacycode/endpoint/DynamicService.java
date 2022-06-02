package com.github.legacycode.endpoint;

import java.net.URI;
import java.util.Set;
import jakarta.json.JsonObject;

public interface DynamicService<E, D> {

    PaginationData<D> onFilter(String name, Set<DynamicQuery> queries);

    D onFind(String name, String id);

    URI onCreate(String name, JsonObject data);

    void onUpdate(String name, JsonObject data, String id);

    void onDelete(String name, String id);

}
