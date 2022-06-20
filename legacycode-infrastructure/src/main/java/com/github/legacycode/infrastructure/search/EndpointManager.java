package com.github.legacycode.infrastructure.search;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Singleton;

@Singleton
public class EndpointManager {

    private final Set<EndpointEntry<?, ?, ?, ?>> entries;

    private EndpointManager() {
        this.entries = new HashSet<>();
    }

    public <E, D, M extends EntityMapper<E, D>, S extends EndpointService> void register(final EndpointEntry<E, D, M, S> entry) {
        if (this.entries.contains(entry)) {
            throw new IllegalArgumentException("Entry already registered : " + entry);
        } else {
            this.entries.add(entry);
        }
    }

    public <E, D, M extends EntityMapper<E, D>, S extends EndpointService> EndpointEntry<E, D, M, S> resolve(final String name) {
        return (EndpointEntry<E, D, M, S>) this.entries
                .stream()
                .filter(d -> Objects.equals(d.getName(), name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No entry registered for name : " + name));
    }

    public <E, D, M extends EntityMapper<E, D>, S extends EndpointService> S invokeService(final String name) {
        EndpointEntry<E, D, M, S> entry = this.resolve(name);
        Class<S> service = entry.getServiceClass();
        return CDI.current().select(service).get();
    }

    public <E, D, M extends EntityMapper<E, D>, S extends EndpointService> M invokeMapper(final EndpointEntry<E, D, M, S> entry) {
        Class<M> mapper = entry.getMapperClass();
        return CDI.current().select(mapper).get();
    }

}
