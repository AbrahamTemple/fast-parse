package fast.parse.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * 安全的时间工具类
 * @Author: Richard.Lee
 * @Date: created by 2021/5/3
 */
public class DateUtils {

	public static Date getDate(String dateStr) throws ParseException {
		if (dateStr.length() > 23) {
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
			return sdf.parse(dateStr);
		} else {
			return dateStr.length() > 8 && dateStr.contains(":") ? getFullDate(dateStr) : getSimpleDate(dateStr);
		}
	}

	public static Date getSimpleDate(String dateStr) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(dateStr);
		} catch (ParseException var2) {
			var2.printStackTrace();
			return null;
		}
	}

	public static Date getFullDate(String dateStr) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateStr.contains("T") ? "yyyy-MM-dd'T'HH:mm:ss" : "yyyy-MM-dd HH:mm:ss");
			return sdf.parse(dateStr);
		} catch (ParseException var2) {
			var2.printStackTrace();
			return null;
		}
	}

	public static String getDateStr(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 得到7天前
	 * @param now
	 * @return
	 */
	public static Date beforeSevenDay(Date now){
		long before = now.getTime() - 86400000 * 7;
		return new Date(before);
	}


	/**
	 * 判断现在是否在thatDate之前, 当thatDate为null时，代表无穷大
	 * @param thatDate
	 * @return
	 */
	public static Boolean nowBefore(Date thatDate){
		Date now = new Date();
		if (Objects.isNull(thatDate)){
			return true;
		}
		return now.before(thatDate);
	}

	/**
	 * 判断现在是否在thatDate之后, 当thatDate为null时，代表无穷大
	 * @param thatDate
	 * @return
	 */
	public static Boolean nowAfter(Date thatDate){
		Date now = new Date();
		if (Objects.isNull(thatDate)){
			return false;
		}
		return now.before(thatDate);
	}
}
