package com.skiff.registry.server.jdbc;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistryServerInfoRowMapper implements RowMapper<RegistryServerInfo> {
    @Override
    public RegistryServerInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        RegistryServerInfo registryServerInfo = new RegistryServerInfo();
        registryServerInfo.setId(rs.getLong("id"));
        registryServerInfo.setName(rs.getString("name"));
        registryServerInfo.setIp(rs.getString("ip"));
        registryServerInfo.setPort(rs.getInt("port"));
        registryServerInfo.setStatus(rs.getInt("status"));
        registryServerInfo.setDescription(rs.getString("description"));
        registryServerInfo.setRegisterTime(rs.getTimestamp("register_time").toLocalDateTime());
        registryServerInfo.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
        registryServerInfo.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
        return registryServerInfo;
    }
}
