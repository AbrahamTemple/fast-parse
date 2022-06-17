package com.boot.jjrepository.hat.fast.parse.handler;

import com.boot.jjrepository.hat.fast.parse.jdbc.BaseConnector;
import com.boot.jjrepository.hat.fast.parse.vo.PreStructVo;
import com.boot.jjrepository.hat.fast.parse.vo.ThenStructVo;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Description
 * @author AbrahamVong
 * @since 17/6/2022
 */
public class BatchWriter extends ExecuteBlock{


    private BaseConnector connector;

    /**
     * 存放数据文件的目录
     */
    private String dir;


    /**
     * 指定后缀名
     */
    private String suffix;


    /**
     * 指定转化的模型对象
     */
    private Class<?> model;


    public BatchWriter(BaseConnector connector, String dir, String suffix, Class<?> model) {
        this.connector = connector;
        this.dir = dir;
        this.suffix = suffix;
        this.model = model;
    }

    public void invoke() throws SQLException, ClassNotFoundException, IOException {
//        BaseConnector connector = new BaseConnector("192.168.0.203", "3306", "t_power_outage_pool", "pop", "Pop@37305229");
        Connection conn = connector.getInstance();
        conn.setAutoCommit(false);

        PreStructVo pre = preExec(dir, suffix);

        for (int i = 1; i < pre.getFilePath().length; i++) {

            ThenStructVo then = thenExec(conn, pre.getTemplate(), i, model);

            endExec(then.getConn(),then.getIt(), model,then.getPstmt());

        }

        System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("解析已结束！").reset());

    }
}
