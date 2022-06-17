package com.boot.jjrepository.hat.fast.parse.jdbc;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Description
 * @author AbrahamVong
 * @since 17/6/2022
 */
public class BaseConnector extends ConnectBuilder implements Connector{

    public BaseConnector(String driver, String ip, String port, String database, String username, String password) {
        super(driver, ip, port, database, username, password);
    }

    public BaseConnector(String ip, String port, String database, String username, String password) {
        super(ip, port, database, username, password);
    }

    @Override
    public Connection getInstance(String ...params) throws ClassNotFoundException, SQLException {
        Class.forName(super.getDriver());
        return DriverManager.getConnection(super.getUrl(), super.getUsername(), super.getPassword());
    }
}
