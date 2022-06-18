package fast.parse.even;

import fast.parse.handler.ExecuteBlock;
import fast.parse.jdbc.BaseConnector;
import fast.parse.vo.PreStructVo;
import fast.parse.vo.ThenStructVo;
import org.fusesource.jansi.Ansi;

import java.sql.Connection;
import java.util.concurrent.Callable;

/**
 * @Description
 * @author AbrahamVong
 * @since 17/6/2022
 */
public class DistributedTask extends ExecuteBlock implements Callable<String> {

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

    private String template;
    private Integer index;

    public DistributedTask(BaseConnector connector, String dir, String suffix, Class<?> model) {
        this.connector = connector;
        this.dir = dir;
        this.suffix = suffix;
        this.model = model;
    }

    public DistributedTask(BaseConnector connector, String dir, String suffix, Class<?> model, String template, Integer index) {
        this.connector = connector;
        this.dir = dir;
        this.suffix = suffix;
        this.model = model;
        this.template = template;
        this.index = index;
    }

    @Override
    public String call() throws Exception {
        Connection conn = connector.getInstance();
        conn.setAutoCommit(false);

        ThenStructVo then = thenExec(conn, template, index, model);

        endExec(then.getConn(),then.getIt(), model,then.getPstmt());

        return Thread.currentThread().getName() + "解析任务已执行完成！";
    }

}
