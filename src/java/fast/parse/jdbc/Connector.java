package fast.parse.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Description
 * @author AbrahamVong
 * @since 17/6/2022
 */
public interface Connector {

    /**
     * 创建连接对象
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    Connection getInstance(String ...params) throws ClassNotFoundException, SQLException;

}
