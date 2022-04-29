package com.github.legacycode.core.common;

import com.github.legacycode.core.LegacyCode;
import com.github.legacycode.core.Identifiable;

public interface DynamicService {

    default <E extends Identifiable<K>, K> K create(
            final DynamicType targetType,
            final E entity) {

        var repository = DynamicService.getRepository();
        var entityClass = targetType.<E, K>getEntityClass();
        var key = entity.getKey();
        var exists = repository.contains(entityClass, key);
        if (exists) {
            var message = String.format("Entity '%s' with key '%s' already exists", entity, key);
            throw new ServiceCreateException(message);
        }
        repository.add(entityClass, entity);

        return key;
    }

    default <E extends Identifiable<K>, K> E read(
            final DynamicType targetType,
            final K key) {

        var repository = DynamicService.getRepository();
        var entityClass = targetType.<E, K>getEntityClass();
        var message = String.format(
                "Entity '%s' not found with key '%s'",
                targetType.getEntityClass(),
                key
        );

        return repository
                .find(entityClass, key)
                .orElseThrow(() -> new ServiceReadException(message));
    }

    default <E extends Identifiable<K>, K> K update(
            final DynamicType targetType,
            final E entity) {

        var repository = DynamicService.getRepository();
        var entityClass = targetType.<E, K>getEntityClass();
        var key = entity.getKey();
        var exists = repository.contains(entityClass, key);
        if (!exists) {
            var message = String.format("Entity '%s' with key '%s' not exists", entity, key);
            throw new ServiceUpdateException(message);
        }
        repository.add(entityClass, entity);

        return key;
    }

    default <E extends Identifiable<K>, K> void delete(
            final DynamicType targetType,
            final K key) {

        var repository = DynamicService.getRepository();
        var entityClass = targetType.<E, K>getEntityClass();
        var exists = repository.contains(entityClass, key);
        if (!exists) {
            var message = String.format("Unknown entity '%s' with key '%s'", entityClass, key);
            throw new ServiceRemoveException(message);
        }
        repository.remove(entityClass, key);
    }

    private static DynamicRepository getRepository() {
        return LegacyCode
                .getCurrentContext()
                .get(DynamicRepository.class);
    }
}
