package com.github.legacycode.endpoint;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Singleton;

@Singleton
public final class EntryManager {

    private final Set<EntityEntry<?, ?, ?, ?>> entries;

    private EntryManager() {
        this.entries = new HashSet<>();
    }

    public <E, D, M extends EntityMapper<E, D>, S extends EndpointService> void register(final EntityEntry<E, D, M, S> entry) {
        this.entries.add(entry);
    }

    public <E, D, M extends EntityMapper<E, D>, S extends EndpointService> EntityEntry<E, D, M, S> resolve(final String name) {
        return (EntityEntry<E, D, M, S>) this.entries
                .stream()
                .filter(d -> Objects.equals(d.name(), name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No entry registered for name : " + name));
    }

    public <E, D, M extends EntityMapper<E, D>, S extends EndpointService> S invokeService(final String name) {

        EntityEntry<E, D, M, S> entry = this.resolve(name);
        Class<S> service = entry.serviceClass();
        return CDI.current().select(service).get();
    }

    public <E, D, M extends EntityMapper<E, D>, S extends EndpointService> M invokeMapper(final EntityEntry<E, D, M, S> entry) {

        Class<M> mapper = entry.mapperClass();
        return CDI.current().select(mapper).get();
    }

}
