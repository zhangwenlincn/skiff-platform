package com.skiff.registry.server.jdbc;

import lombok.Getter;

@Getter
public enum DataDialectEnum {

    PostgreSQL("SELECT current_schema()","SELECT COUNT(*) as cnt FROM information_schema.tables WHERE table_schema = ? AND table_name = 'registry_server_info'", """
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

    MySQL("SELECT DATABASE()","SELECT COUNT(*) as cnt FROM information_schema.tables WHERE table_schema = ? AND table_name ='registry_server_info'", """
            CREATE TABLE `registry_server_info` (
              `id` bigint unsigned NOT NULL AUTO_INCREMENT,
              `name` varchar(100) DEFAULT NULL,
              `ip` varchar(50) DEFAULT NULL,
              `port` int DEFAULT NULL,
              `register_time` datetime DEFAULT NULL,
              `description` varchar(100) DEFAULT NULL,
              `status` int NOT NULL DEFAULT '1',
              `create_time` datetime DEFAULT NULL,
              `update_time` datetime DEFAULT NULL,
              PRIMARY KEY (`id`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci""", "MySQL"),
    ;

    private final String schema;

    private final String sql;

    private final String initSql;

    private final String description;

    DataDialectEnum(String schema,String sql, String initSql, String description) {
        this.schema = schema;
        this.sql = sql;
        this.initSql = initSql;
        this.description = description;
    }
}
