package com.example.recommendations.config;

import org.hibernate.dialect.Dialect;

/**
 * Lightweight SQLite dialect placeholder.
 *
 * This class exists to provide a concrete Dialect type in-project if you want
 * to reference a dialect class explicitly via `spring.jpa.properties.hibernate.dialect`.
 * It intentionally avoids overriding Hibernate internals so it remains
 * compatible across Hibernate 6.x versions used in this project.
 */
public class SQLiteDialect extends Dialect {
    public SQLiteDialect() {
        super();
    }
}
