package fast.parse.utils;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description
 * @author AbrahamVong
 * @since 17/6/2022
 */
public class FunctionTo {

    public static String separator = "\u001B";

    @SneakyThrows
    public static String[] getDirFiles(String dir, String suffix){
        Set<String> pkgSet = new HashSet<>();

        Files.walk(Paths.get(dir)).filter(Files::isRegularFile).forEach(path ->{
            if(path.toString().substring(path.toString().lastIndexOf(".")+1).equals(suffix)){
                pkgSet.add(path.toString());
            }
        });

        return pkgSet.stream().toArray(String[]::new);
    }

    @SneakyThrows
    public static String dotValue(String[] row, boolean isField){
        StringBuilder sum = new StringBuilder();
        for (int i = 0; i < row.length; i++) {
            if(row[i].contains("'"))
                row[i] = row[i].replace("'", "\\'");

            if(row[i].contains(separator))
                row[i] = row[i].replace(separator, " ");

            if(i != row.length - 1){
                if(Pretreatment.isNumeric(row[i])) {
                    sum.append(row[i]).append(",");
                }
                else if(Pretreatment.isNon(row[i])){
                    sum.append("NULL").append(",");
                }
                else{
                    if(isField) {
                        sum.append(Pretreatment.camel4underline(row[i])).append(",");
                    } else {
                        sum.append("'").append(row[i]).append("'").append(",");
                    }
                }
            } else {
                if(Pretreatment.isNumeric(row[i])) {
                    sum.append(row[i]);
                }
                else if(Pretreatment.isNon(row[i])){
                    sum.append("NULL");
                }
                else{

                    if(isField) {
                        sum.append(Pretreatment.camel4underline(row[i]));
                    } else {
                        sum.append("'").append(row[i]).append("'");
                    }
                }
            }
        }
        return sum.toString();
    }

    public static String fdOut(String[] row){
        StringBuilder query = new StringBuilder();
        for (int i = 0; i < row.length; i++) {
            if(i != row.length-1){
                query.append("?,");
            } else {
                query.append("?");
            }
        }
        return query.toString();
    }

    /**
     * 设置行分隔符
     * @param separator
     */
    public static void setSeparator(String separator) {
        FunctionTo.separator = separator;
    }
}
