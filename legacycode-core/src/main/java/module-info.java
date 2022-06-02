module legacy.core {

    requires java.base;
    requires java.sql;

    requires legacycode.common;

    exports com.github.legacycode.core;
    exports com.github.legacycode.core.common;
    exports com.github.legacycode.core.customer;
    exports com.github.legacycode.core.gender;

    exports com.github.legacycode.internal.persistence.collection;
    exports com.github.legacycode.internal.persistence.jdbc;
    exports com.github.legacycode.internal.service;

}
