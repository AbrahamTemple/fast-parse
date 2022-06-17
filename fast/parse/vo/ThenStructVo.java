package com.boot.jjrepository.hat.fast.parse.vo;

import org.apache.commons.io.LineIterator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;

/**
 * @Description
 * @author AbrahamVong
 * @since 17/6/2022
 */
public class ThenStructVo {

    private Connection conn;

    private LineIterator it;

    private PreparedStatement pstmt;

    public ThenStructVo(Connection conn, LineIterator it, PreparedStatement pstmt) {
        this.conn = conn;
        this.it = it;
        this.pstmt = pstmt;
    }

    public LineIterator getIt() {
        return it;
    }

    public void setIt(LineIterator it) {
        this.it = it;
    }

    public PreparedStatement getPstmt() {
        return pstmt;
    }

    public void setPstmt(PreparedStatement pstmt) {
        this.pstmt = pstmt;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
