package fast.parse.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @author AbrahamVong
 * @since 17/6/2022
 */
public class Pretreatment {


    public static String empty = "\\N";

    public static <T> void builderData(Class<T> clazz, String[] values, PreparedStatement pstmt) throws SQLException, ParseException {

        Field[] fields = clazz.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {

            if (fields[i].getType() == String.class) {

                if(null != validateNull(values[i])){

                    pstmt.setString(i+1, values[i]);

                } else {

                    pstmt.setNull(i+1, Types.VARCHAR);

                }

            } else if (fields[i].getType() == Date.class) {

                if(null != validateNull(values[i]) && isDate(values[i])) {


                    pstmt.setDate(i+1, new java.sql.Date(fast.parse.common.DateUtils.getDate(values[i]).getTime()));

                } else {

                    pstmt.setNull(i+1,Types.DATE);

                }

            } else if(fields[i].getType() == Double.class) {

                if(null != validateNull(values[i])) {

                    pstmt.setDouble(i+1, Double.parseDouble(validateNull(values[i])));

                } else {

                    pstmt.setNull(i+1,Types.DOUBLE);

                }

            } else if (fields[i].getType() == Integer.class) {

                if(null != validateNull(values[i])) {

                    pstmt.setInt(i+1, Integer.parseInt(validateNull(values[i])));

                } else {

                    pstmt.setNull(i+1,Types.INTEGER);

                }

            } else if (fields[i].getType() == Long.class) {

                if(null != validateNull(values[i])) {

                    pstmt.setLong(i+1, Long.parseLong(validateNull(values[i])));

                } else {

                    pstmt.setNull(i+1,Types.BIGINT);

                }

            } else if (fields[i].getType() == Boolean.class){

                if(null != validateNull(values[i])) {

                    pstmt.setBoolean(i+1, Boolean.parseBoolean(validateNull(values[i])));

                } else {

                    pstmt.setNull(i+1,Types.BIT);

                }

            }

        }

    }

    /**
     * 验证是否是空数据
     * @param str 被切割的每个数据
     * @return
     */
    public static String validateNull(String str){
        return str == null || str.contains(empty) ? null : str;
    }

    public static Boolean isNumeric(String str) {
        return str != null && str.matches("-?\\d+(\\.\\d+)?");
    }

    public static Boolean isNon(String str){
        return str != null && str.contains(empty);
    }

    public static Boolean isDate(String str) {
        try {
            DateUtils.parseDate(str, "yyyy-MM-dd HH:mm:ss");
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 首字母大写其他小写
     * @param name
     * @return
     */
    public static String toUpperLower(String name){
        return name.substring(0, 1).toUpperCase() +
                name.substring(1);
    }

    /**
     * 驼峰命名法转化
     * @param param
     * @return
     */
    public static String camel4underline(String param){
        Pattern p=Pattern.compile("[A-Z]");
        if(param==null ||param.equals("")){
            return "";
        }
        StringBuilder builder=new StringBuilder(param);
        Matcher mc=p.matcher(param);
        int i=0;
        while(mc.find()){
            builder.replace(mc.start()+i, mc.end()+i, "_"+mc.group().toLowerCase());
            i++;
        }

        if('_' == builder.charAt(0)){
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }

    /**
     * 设置行空符
     * @param empty
     */
    public static void setEmpty(String empty) {
        Pretreatment.empty = empty;
    }
}
