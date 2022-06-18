package fast.parse;

import fast.parse.handler.BatchWriter;
import fast.parse.jdbc.BaseConnector;
import model.DmFunctionLocationM;
import lombok.SneakyThrows;

/**
 * @Description
 * @author AbrahamVong
 * @since 17/6/2022
 */
public class Example {

    @SneakyThrows
    public static void main(String[] args) {

        BaseConnector connector = new BaseConnector("127.0.0.1", "3306", "pool", "root", "123456");

        String dir = "F:\\somedata\\pop\\O_DGM_ODS_08_SBZX03_PSR_RUN_DM_FUNCTION_LOCATION_M_split_14";

        String suffix = "dat";

        Class<?> clazz = DmFunctionLocationM.class;

        BatchWriter batch = new BatchWriter(connector, dir, suffix, clazz);

        batch.invoke();
    }
}
