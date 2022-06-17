package com.boot.jjrepository.hat.fast.parse.jdbc;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description
 * @author AbrahamVong
 * @since 17/6/2022
 */
public abstract class ConnectBuilder {

    /**
     * 驱动
     */
    private String driver;

    /**
     * ipv4地址
     */
    private String ip;

    /**
     * 端口
     */
    private String port;

    /**
     * 数据库
     */
    private String database;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * MySQL默认连接URL模板
     */
    private final String MYSQL_URL = "jdbc:mysql://%s:%s/%s";


    public ConnectBuilder(String driver, String ip, String port, String database, String username, String password) {
        this.driver = driver;
        this.ip = ip;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public ConnectBuilder(String ip, String port, String database, String username, String password) {
        this.ip = ip;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    /**
     * 获取驱动
     * @return
     */
    public String getDriver() {
        return !StringUtils.isEmpty(driver) ? driver : "com.mysql.cj.jdbc.Driver";
    }

    /**
     * 获取连接URL
     * @param param URL参数
     * @return
     */
    public String getUrl(String ...param) {
        if(param.length == 0)
            return String.format(MYSQL_URL,ip,port,database);
        else{
            StringBuilder buff = new StringBuilder();
            buff.append("?");
            for (int i = 0; i < param.length; i++) {
                buff.append(param[i]);
                if((i+1) != param.length){
                    buff.append("&");
                }
            }
            return String.format(MYSQL_URL,ip,port,database) + buff.toString();
        }
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
