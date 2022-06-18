package fast.parse.handler;

import fast.parse.utils.FunctionTo;
import fast.parse.utils.Pretreatment;
import fast.parse.vo.PreStructVo;
import fast.parse.vo.ThenStructVo;
import fast.parse.common.ObjectUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.fusesource.jansi.Ansi;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @Description
 * @author AbrahamVong
 * @since 17/6/2022
 */
public abstract class ExecuteBlock {

    public PreStructVo preExec(String dir, String suffix){
        String[] filePath = FunctionTo.getDirFiles(dir, suffix);
        if (filePath.length == 0){
            return new PreStructVo("",filePath);
        }
        File var1 = new File(filePath[0]);
        return new PreStructVo(var1.getPath().replace(var1.getName(),"") + File.separator + var1.getName().substring(0,var1.getName().lastIndexOf(".")-1) + "%s" + "." + suffix,filePath);
    }

    public ThenStructVo thenExec(Connection conn, String template, Integer index, Class<?> clazz) throws IOException, SQLException {
        File parseFile = new File(String.format(template,(index+1)));

        LineIterator it = FileUtils.lineIterator(parseFile, "UTF-8");

        String tableName = Pretreatment.camel4underline(clazz.getSimpleName());

        String[] objectFields = ObjectUtils.getObjectFields(clazz, "serialVersionUID").toArray(new String[0]);

        String sql = "INSERT IGNORE INTO "+ tableName + " (" + FunctionTo.dotValue(objectFields,true) + ") values(" + FunctionTo.fdOut(objectFields)+ ");";

        PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        conn.setAutoCommit(false);

        return new ThenStructVo(conn, it,pstmt);
    }

    public void endExec(Connection conn,LineIterator it,Class<?> clazz,PreparedStatement pstmt) throws SQLException, ClassNotFoundException {

        String tableName = Pretreatment.camel4underline(clazz.getSimpleName());

        String[] objectFields = ObjectUtils.getObjectFields(clazz, "serialVersionUID").toArray(new String[0]);

        try {
            while (it.hasNext()) {
                String line = it.nextLine();

                String[] row  = line.split(FunctionTo.separator);

                Pretreatment.builderData(clazz, row, pstmt);

                pstmt.addBatch();

                String loadSql = Ansi.ansi().fg(Ansi.Color.GREEN).a("insert into").reset() + " " +
                        Ansi.ansi().fg(Ansi.Color.RED).a(tableName).reset() + " " +
                        Ansi.ansi().fg(Ansi.Color.CYAN).a("(" + FunctionTo.dotValue(objectFields,false) + ")").reset() + " " +
                        Ansi.ansi().fg(Ansi.Color.MAGENTA).a("values(" + FunctionTo.dotValue(row,true)+ ");").reset();

                System.out.println(loadSql);

            }
        } catch (Exception e){

            System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("解析发生异常！").reset());
            System.out.println(Ansi.ansi().fg(Ansi.Color.CYAN).a(e.getMessage()).reset());

        } finally {
            Date now = new Date();
            System.out.println(Ansi.ansi().fg(Ansi.Color.DEFAULT).a("正在提交事务...").reset());
            LineIterator.closeQuietly(it);
            pstmt.executeBatch();
            conn.commit();
            Date end = new Date();
            System.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW).a("已经使用了" + (end.getTime() - now.getTime())/1000 + "s").reset());
        }
    }
}
