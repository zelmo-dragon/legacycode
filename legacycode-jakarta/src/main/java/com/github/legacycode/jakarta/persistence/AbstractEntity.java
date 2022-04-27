package com.github.legacycode.jakarta.persistence;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    UUID id;

    @Version
    @Column(name = "version", nullable = false, unique = true)
    Long version;

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

}

