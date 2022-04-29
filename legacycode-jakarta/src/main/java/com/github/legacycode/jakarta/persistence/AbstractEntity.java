package com.github.legacycode.jakarta.persistence;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractEntity implements Entity<UUID>, Serializable {

    @Id
    @Column(name = "id", nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    protected UUID id;

    @Version
    @Column(name = "version", nullable = false, unique = true)
    protected Long version;

    protected AbstractEntity() {
        this.id = UUID.randomUUID();
        this.version = 0L;
    }

    @Override
    public boolean equals(Object o) {
        boolean eq;
        if (this == o) {
            eq = true;
        } else if (o == null || getClass() != o.getClass()) {
            eq = false;
        } else {
            var that = (AbstractEntity) o;
            eq = Objects.equals(id, that.id)
                    && Objects.equals(version, that.version);
        }
        return eq;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(final UUID id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(final Long version) {
        this.version = version;
    }
}

