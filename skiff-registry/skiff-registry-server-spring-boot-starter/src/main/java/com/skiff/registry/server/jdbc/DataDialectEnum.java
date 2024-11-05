package com.skiff.registry.server.jdbc;

import lombok.Getter;

@Getter
public enum DataDialectEnum {

    PostgreSQL("SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'registry_server_info'", """
            CREATE TABLE public.registry_server_info (
            	id SERIAL PRIMARY KEY,
            	"name" varchar(255) NULL,
            	ip varchar(255) NULL,
            	port int4 NULL,
            	register_time timestamp(6) NOT NULL,
            	description varchar(255) NULL,
            	status int4 NULL,
            	create_time timestamp(6) NULL,
            	update_time timestamp(6) NULL
            )""", "PostgreSQL"),

    SQLite("SELECT COUNT(*) FROM sqlite_master where table = 'table' and name = 'registry_server_info'", """
            CREATE TABLE registry_server_info (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                ip TEXT,
                port INTEGER,
                register_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                description TEXT,
                status INTEGER,
                create_time TIMESTAMP,
                update_time TIMESTAMP
            )""", "SQLite"),
    ;
    private final String sql;

    private final String initSql;

    private final String description;

    DataDialectEnum(String sql, String initSql, String description) {
        this.sql = sql;
        this.initSql = initSql;
        this.description = description;
    }
}
