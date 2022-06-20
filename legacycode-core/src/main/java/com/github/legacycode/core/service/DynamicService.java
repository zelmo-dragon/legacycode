package com.github.legacycode.core.service;

import com.github.legacycode.core.repository.Identifiable;
import com.github.legacycode.core.LegacyCode;
import com.github.legacycode.core.repository.DynamicRepository;
import com.github.legacycode.core.DynamicType;

public interface DynamicService {

    default <K, E extends Identifiable<K>> K create(
            final DynamicType targetType,
            final E entity) {

        var repository = DynamicService.getRepository();
        var entityClass = targetType.<E, K>getEntityClass();
        var key = entity.getId();
        var exists = repository.contains(entityClass, key);
        if (exists) {
            var message = String.format("Entity '%s' with key '%s' already exists", entity, key);
            throw new ServiceException(message);
        }
        repository.add(entityClass, entity);

        return key;
    }

    default <K, E extends Identifiable<K>> E read(
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
                .orElseThrow(() -> new ServiceException(message));
    }

    default <K, E extends Identifiable<K>> K update(
            final DynamicType targetType,
            final E entity) {

        var repository = DynamicService.getRepository();
        var entityClass = targetType.<E, K>getEntityClass();
        var key = entity.getId();
        var exists = repository.contains(entityClass, key);
        if (!exists) {
            var message = String.format("Entity '%s' with key '%s' not exists", entity, key);
            throw new ServiceException(message);
        }
        repository.add(entityClass, entity);

        return key;
    }

    default <K, E extends Identifiable<K>> void delete(
            final DynamicType targetType,
            final K key) {

        var repository = DynamicService.getRepository();
        var entityClass = targetType.<E, K>getEntityClass();
        var exists = repository.contains(entityClass, key);
        if (!exists) {
            var message = String.format("Unknown entity '%s' with key '%s'", entityClass, key);
            throw new ServiceException(message);
        }
        repository.remove(entityClass, key);
    }

    private static DynamicRepository getRepository() {
        return LegacyCode
                .getCurrentContext()
                .get(DynamicRepository.class);
    }
}
