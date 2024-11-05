package com.skiff.registry.server.service;

import com.skiff.common.core.exception.SkiffException;
import com.skiff.protobuf.registry.RpcRegistry;
import com.skiff.registry.server.bean.RegisteredServer;
import com.skiff.registry.server.jdbc.DataDialectEnum;
import com.skiff.registry.server.jdbc.RegistryServerInfo;
import com.skiff.registry.server.jdbc.RegistryServerInfoRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class JdbcRegisteredServiceImpl implements RegisteredService {


    private static final Logger log = LoggerFactory.getLogger(JdbcRegisteredServiceImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public JdbcRegisteredServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        DatabaseMetaData data = Optional.ofNullable(jdbcTemplate.execute(Connection::getMetaData)).orElseThrow(() -> new SkiffException("get db info error"));
        try {
            DataDialectEnum dataDialectEnum = DataDialectEnum.valueOf(data.getDatabaseProductName());
            String schema = jdbcTemplate.queryForObject(dataDialectEnum.getSchema(), String.class);
            Map<String, Object> cntMap = jdbcTemplate.queryForMap(dataDialectEnum.getSql(), schema);
            if ((Long) cntMap.get("cnt") == 0) {
                log.info("jdbc init db");
                jdbcTemplate.execute(dataDialectEnum.getInitSql());
            }
        } catch (SQLException e) {
            throw new SkiffException("get database product name err");
        }
    }


    @Override
    public void registry(RpcRegistry.RegistryRequest request) {
        List<RegistryServerInfo> registryServerInfoList = getByName(request.getServiceName());
        Optional<RegistryServerInfo> db = registryServerInfoList.stream().filter(x -> Objects.equals(x.getIp(), request.getIp()) && Objects.equals(x.getPort(), request.getPort())).findFirst();
        if (db.isPresent()) {
            jdbcTemplate.update("update registry_server_info set register_time = ?,update_time = NOW() where id = ? and status = 1", LocalDateTime.now(), db.get().getId());
        } else {
            jdbcTemplate.update("insert into registry_server_info(name, ip, port, register_time, status, create_time, update_time) values(?, ?, ?, ?,1,NOW(),NOW())"
                    , request.getServiceName()
                    , request.getIp()
                    , request.getPort()
                    , LocalDateTime.now());
        }
    }

    public void expired() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    removeExpired();
                } catch (Exception e) {
                    log.error("remove expired error", e);
                } finally {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public List<RegisteredServer> getRegistered() {
        String sql = "select * from registry_server_info where status = 1";
        List<RegistryServerInfo> registryServerInfoList = jdbcTemplate.query(sql, new RegistryServerInfoRowMapper());
        if (CollectionUtils.isEmpty(registryServerInfoList)) {
            return new ArrayList<>();
        }
        Map<String, List<RegistryServerInfo>> listMap = registryServerInfoList.stream().collect(Collectors.groupingBy(RegistryServerInfo::getName));
        return listMap.entrySet().stream().map(x -> new RegisteredServer(x.getKey(), x.getValue().stream().map(y -> y.getIp() + ":" + y.getPort()).collect(Collectors.toSet()))).collect(Collectors.toList());
    }

    @Override
    public RegisteredServer getRegistered(String serviceName) {
        List<RegistryServerInfo> dbList = Optional.ofNullable(getByName(serviceName)).orElseThrow(() -> new SkiffException("service not exits"));
        return new RegisteredServer(serviceName, dbList.stream().map(x -> x.getIp() + ":" + x.getPort()).collect(Collectors.toSet()));
    }

    public void removeExpired() {
        int cnt = jdbcTemplate.update("delete from registry_server_info where status = 1 and register_time < ?", LocalDateTime.now().plusSeconds(-8));
        if (cnt > 0) {
            log.info("remove expired {}", cnt);
        }
    }

    public List<RegistryServerInfo> getByName(String name) {
        return jdbcTemplate.query("select * from registry_server_info where name = ? and status = 1", new RegistryServerInfoRowMapper(), name);
    }
}
