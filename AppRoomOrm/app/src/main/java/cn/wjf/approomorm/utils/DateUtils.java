package cn.wjf.approomorm.utils;

import android.text.TextUtils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class DateUtils {

    public static final String DATE_FORMAT_DAY = "yyyy-MM-dd";

    public static final String DATE_FORMAT_MONTH = "yyyy-MM";

    public static final String DATE_FORMAT_HOUR = "yyyy-MM-dd HH";

    public static final String DATE_FORMAT_MINUTE = "yyyy-MM-dd HH:mm";

    public static final String DATE_FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT_SECOND_T = "yyyy-MM-dd'T'HH:mm:ss";

    public static final String DATE_FORMAT_MILLISECOND = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String DATE_FORMAT_SECOND_T_PLUS = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    public static final String DATE_FORMAT_CHINESE = "yyyy年MM月dd日";

    public static final String DATE_FORMAT_MINUTE_CHINESE = "yyyy年MM月dd日 HH时mm分";

    public static final String DATE_FORMAT_SPRIT = "dd/MM/yyyy";

    public static final String DATE_FORMAT_DAY_NOTKG = "yyyyMMdd";

    public static final String DATE_FORMAT_DAY_POINT = "yyyy.MM.dd";

    public static final String DATE_FORMAT_TIME_POINT = "HH:mm";

    public static final String DATE_FORMAT_TIME_H_M_SECOND = "HH:mm:ss";

    public static final String DATE_FORMAT_DAY_TIME = "MM/dd HH:mm";

    public static final String DATE_FORMAT_DAY_TIME2 = "MM-dd HH:mm";

    public static final String TIME_ON_WORK = " 08:00:00";
    public static final String TIME_OFF_WORK = " 18:00:00";

    public static final String DATE_FORMAT_MONTH_DAY_TIME = "MM/dd";
    public static final String DATE_FORMAT_YYMMDDHHMM = "yyMMddHHmm";
    public static final String DATE_FORMAT_DAY_HOUR_MINITER_TIME = "dd/HH:mm";
    public static final String DATE_FORMAT_DAY_MONTH_YEAR_TIME = "dd-MM-yyyy HH:mm";
    public static final String DATE_FORMAT_YYMMDDHHMMEEEE = "yyyy-MM-dd HH:mm EEEE";
    public static SimpleDateFormat mFormatHM = new SimpleDateFormat("HH:mm");

    public static final String DATE_FORMAT_MMdd = "MM-dd";
    public static final String DATE_FORMAT_YYMMdd = "yy/MM/dd";


    /**
     * 获取系统当前时间long型
     *
     * @return
     */
    public static long getCurrentDateTime() {
        return new Date().getTime();
    }

    /**
     * 获取系统当前时间long型
     *
     * @return
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 返回当前时间
     *
     * @return 返回当前时间
     */
    public static Date getCurrentDate() {
        Calendar calNow = Calendar.getInstance();
        return calNow.getTime();
    }

    public static Long getDateToLong(String str) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy年MM月dd日").parse(str);
        } catch (ParseException e) {
        }
        return date.getTime();
    }

    public static String formatTimestamp(Timestamp timestamp) {
        return new SimpleDateFormat(DATE_FORMAT_DAY).format(timestamp);
    }

    /**
     * 格式化日期时间到秒
     *
     * @return
     */
    public static String formatDateOfSecond() {
        return new SimpleDateFormat(DATE_FORMAT_SECOND).format(new Date());
    }

    /**
     * 格式化日期时间到秒
     *
     * @return
     */
    public static String getCurrentDate(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }

    public static String formatDateForChinese() {
        return new SimpleDateFormat(DATE_FORMAT_MINUTE_CHINESE).format(new Date());
    }


    /**
     * 格式化日期带毫秒数
     *
     * @return
     */
    public static String formatDateAndMillisecond() {
        return new SimpleDateFormat(DATE_FORMAT_MILLISECOND).format(new Date());
    }

    /**
     * 格式化日期：年月日
     *
     * @return
     */
    public static String formatDate() {
        return new SimpleDateFormat(DATE_FORMAT_DAY).format(new Date());
    }

    /**
     * 格式化日期：年月日
     *
     * @return
     */
    public static String formatDate(long l) {
        return new SimpleDateFormat(DATE_FORMAT_DAY).format(new Date(l));
    }

    /**
     * 格式化日期：年月日
     *
     * @return
     */
    public static String formatDate(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat(DATE_FORMAT_DAY).format(date);
    }

    /**
     * 格式化日期：年月日
     *
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null)
            return "";
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 格式化日期：年月日
     *
     * @return
     */
    public static String formatDate(String str, String desPattern) {
        if (TextUtils.isEmpty(str) || str.equals("0"))
            return "0";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DAY_NOTKG);
        String result = "";
        try {
            Date d = sdf.parse(str);
            result = new SimpleDateFormat(desPattern).format(d);
        } catch (ParseException e) {
        }
        return result;
    }

    /**
     * 格式化日期：年月日
     *
     * @return
     */
    public static String formatDate(String str, String srcPattern, String desPattern) {
        if (TextUtils.isEmpty(str) || str.equals("null"))
            return "";
        if (str.indexOf("T") != -1) {
            str = str.replace("T", " ");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(srcPattern);
        String result = "";
        try {
            Date d = sdf.parse(str);
            result = new SimpleDateFormat(desPattern).format(d);
        } catch (ParseException e) {
        }
        return result;
    }

    /**
     * 格式化日期：年月日 时
     *
     * @param l
     * @return
     */
    public static String formatDateTime(long l) {
        return new SimpleDateFormat(DATE_FORMAT_HOUR).format(new Date(l));
    }

    /**
     * 格式化日期：年月日 时分
     *
     * @param l
     * @return
     */
    public static String formatDateTimeminute(long l) {
        return new SimpleDateFormat(DATE_FORMAT_MINUTE).format(new Date(l));
    }
    /**
     * 格式化日期：年月日 时分 星期
     *
     * @param l
     * @return
     */
    public static String formatDateTimeEeee(long l) {
        return new SimpleDateFormat(DATE_FORMAT_YYMMDDHHMMEEEE).format(new Date(l));
    }

    /**
     * 格式化日期：年月日 时分秒
     *
     * @param l
     * @return
     */
    public static String formatDateTimesecond(long l) {
        return new SimpleDateFormat(DATE_FORMAT_SECOND).format(new Date(l));
    }

    /**
     * 格式化日期：年月日 时分秒
     *
     * @param date
     * @return
     */
    public static String formatDateTimesecond(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat(DATE_FORMAT_SECOND).format(date);
    }

    /**
     * 格式化日期：年月日 时分秒
     *
     * @param date
     * @return
     */
    public static String formatDateTimesecond(Date date, String pattern) {
        if (date == null)
            return "";
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 格式化日期：年月日
     *
     * @param date
     * @return
     */
    public static String formatDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        try {
            Date d = sdf.parse(date);
            return new SimpleDateFormat(DATE_FORMAT_DAY).format(d);
        } catch (ParseException e) {
        }
        return null;
    }

    public static Date formatDateByString(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        try {
            return sdf.parse(strDate);
        } catch (ParseException e) {
        }
        return null;
    }

    public static Date parseMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_MONTH);
        try {
            return sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
        }
        return null;
    }

    public static Date parseDateTime(String strDate) {
        if (strDate == null || "".equals(strDate)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_SECOND);
        try {
            return sdf.parse(strDate);
        } catch (ParseException e) {
        }
        return null;
    }

    public static Date parseDate(String strDate) {
        if (strDate == null || "".equals(strDate)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DAY);
        try {
            return sdf.parse(strDate);
        } catch (ParseException e) {
        }
        return null;
    }

    public static Date parseDate(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DAY);
        try {
            return sdf.parse(sdf.format(date));
        } catch (ParseException e) {
        }
        return null;
    }

    public static Date parseDate(String strDate, String pattern) {
        if (strDate == null || "".equals(strDate)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(strDate);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 格式化日期：时分秒
     *
     * @return
     */
    public static String formatTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    /**
     * 1.图片命名，精确到毫秒
     *
     * @return
     */
    public static String picMillisecondName() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

    /**
     * 1.图片命名，精确到秒
     *
     * @return
     */
    public static String picSecondName() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    /**
     * 计算某年某月某日某点到当日的天数
     *
     * @return
     */
    public static int calDays(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_SECOND);
        long l = 0l;
        try {
            l = (new Date()).getTime() - (sdf.parse(s)).getTime();
        } catch (ParseException e) {
        }
        return (int) (l / 1000 / 60 / 60 / 24);
    }

    /**
     * 比较日期的大小；当天之前：-1，当天：0，当天以后：1+.
     *
     * @param sqlDate
     * @return
     */
    public static int compareToDate(String sqlDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String temp = null;
        try {
            if (sqlDate != null && !sqlDate.equals("")) {
                temp = sdf.format(sdf.parse(sqlDate));
            } else {
                return 1;
            }
        } catch (ParseException e) {
        }
        return temp.compareTo(sdf.format(new Date()));
    }

    /**
     * 比较日期的大小；当天之前：-1，当天：0，当天以后：1+.
     *
     * @param sqlDate
     * @return
     */
    public static int compare2date(String sqlDate, String pattern) {
        if (TextUtils.isEmpty(sqlDate) || sqlDate.equals("0")) {
            return -1;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date temp = null;
        try {
            if (sqlDate != null && !sqlDate.equals("")) {
                temp = sdf.parse(sqlDate);
            } else {
                return -1;
            }
        } catch (ParseException e) {
        }
        return temp.getTime() > new Date().getTime() ? 1 : -1;
    }

    /**
     * 比较时间的大小；大于当前时间：false，小于当前时间：true.
     *
     * @param sqlDate
     * @return
     */
    public static boolean betweenScope(String sqlDate, String pattern) {
        if (TextUtils.isEmpty(sqlDate) || sqlDate.equals("0")) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        long sqlTime = 0;
        try {
            sqlTime = sdf.parse(sqlDate).getTime();
        } catch (ParseException e) {
        }
        return new Date().getTime() > sqlTime;
    }

    /**
     * 比较日期的大小；当天之前：-1，当天：0，当天以后：1+.
     *
     * @param firstDate
     * @param secondDate
     * @return
     */
    public static int compareToDate(String firstDate, String secondDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String temp1 = null;
        String temp2 = null;
        try {
            if (firstDate != null && !firstDate.equals("")) {
                temp1 = sdf.format(sdf.parse(firstDate));
            } else {
                return 1;
            }
            if (secondDate != null && !secondDate.equals("")) {
                temp2 = sdf.format(sdf.parse(secondDate));
            } else {
                return 1;
            }
        } catch (ParseException e) {
        }
        return temp1.compareTo(temp2);
    }

    /**
     * 比较日期的大小；当天之前：-1，当天：0，当天以后：1+.
     *
     * @param sqlDate
     * @return
     */
    public static int compareToDate(Long sqlDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DAY);
        String temp = null;
        if (sqlDate != null && !sqlDate.equals("")) {
            temp = sdf.format(new Date(sqlDate));
        } else {
            return 1;
        }
        return temp.compareTo(sdf.format(new Date()));
    }

    /**
     * 比较日期的大小；当天之前：-1，当天：0，当天以后：1+.
     *
     * @param historyDate
     * @return
     */
    public static int checkHistoryDate(Date historyDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DAY);
        String temp = null;
        if (historyDate != null && !historyDate.equals("")) {
            temp = sdf.format(historyDate);
        } else {
            return -1;
        }
        return temp.compareTo(sdf.format(new Date()));
    }

    /**
     * 比较日期的大小；当天之前：-1，当天：0，当天以后：1+.
     *
     * @param historyDate
     * @return
     */
    public static int checkHistoryDate(Long historyDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DAY);
        String temp = null;
        if (historyDate != null && !historyDate.equals("")) {
            temp = sdf.format(new Date(historyDate));
        } else {
            return -1;
        }
        return temp.compareTo(sdf.format(new Date()));
    }

    /**
     * 判断某一时间是否在指定的时间段内(包含与起止时间相等的情况)
     *
     * @param time
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean betweenScope(Timestamp time, Timestamp beginTime, Timestamp endTime) {
        return (!time.before(beginTime) && !time.after(endTime));
    }

    /**
     * 判断某一时间是否在指定的时间段内(包含与起止时间相等的情况)
     *
     * @param date
     * @param beginDate
     * @param endDate
     * @return
     */
    public static boolean betweenScope(Date date, Date beginDate, Date endDate) {
        return (!date.before(beginDate) && !date.after(endDate));
    }

    /**
     * 判断某一时间是否在指定的时间段内(包含与起止时间相等的情况)
     *
     * @param date
     * @param beginDate
     * @param endDate
     * @return
     */
    public static boolean betweenScope(Date date, String beginDate, String endDate) {
        return (!date.before(parseDate(beginDate)) && !date.after(parseDate(endDate)));
    }

    /**
     * 判断某一时间是否在指定的时间段内(包含与起止时间相等的情况)
     *
     * @param date
     * @param beginDate
     * @param endDate
     * @return
     */
    public static boolean betweenScope(Date date, String beginDate, String endDate, String pattern) {
        return (!date.before(parseDate(beginDate, pattern)) && !date.after(parseDate(endDate, pattern)));
    }

    /**
     * 判断某一时间是否在指定的时间段内(包含与起止时间相等的情况)
     *
     * @param date
     * @param beginDate
     * @param endDate
     * @return
     */
    public static boolean betweenScope(String date, String beginDate, String endDate) {
        return betweenScope(parseDate(date), beginDate, endDate);
    }

    /**
     * 判断某一时间是否在指定的时间段内(包含与起止时间相等的情况)
     *
     * @param date
     * @param beginDate
     * @param endDate
     * @param pattern
     * @return
     */
    public static boolean betweenScope(String date, String beginDate, String endDate, String pattern) {
        return betweenScope(parseDate(date, pattern), beginDate, endDate);
    }

    /**
     * 判断某一时间是否在指定的时间段内(包含与起止时间相等的情况)
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static boolean compareDate(Date beginDate, Date endDate) {
        return !beginDate.after(endDate);
    }

    /**
     * 判断某一时间是否在指定的时间之后(包含与指定时间相等的情况)
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static boolean compareDate(String beginDate, Date endDate) {
        if ("".equals(beginDate) || beginDate == null) {
            return false;
        }
        return compareDate(parseDate(beginDate), endDate);
    }

    public static boolean compareDate(String beginDate, String endDate) {
        if ("".equals(beginDate) || beginDate == null) {
            return false;
        }
        return compareDate(parseDate(beginDate), parseDate(endDate));
    }

    public static boolean compareDate(String beginDate, String endDate, String pattern) {
        if ("".equals(beginDate) || beginDate == null) {
            return false;
        }
        return compareDate(parseDate(beginDate, pattern), parseDate(endDate, pattern));
    }

    public static boolean compareDate(long beginDate, long endDate) {
        return beginDate < endDate;
    }

    /**
     * 某一时间在指定的时间段内
     */
    public static final String TIME_SCOPE_BEGIN = " 08:30:00";   //某时间段的开始时间
    public static final String TIME_SCOPE_END = " 17:30:00";    //某时间段的结束时间

    /**
     * 判断某一时间是否在指定的时间段内(包含与起止时间相等的情况)
     *
     * @return
     */
    public static boolean betweenScope() {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        String currentDate = time.toString().substring(0, 10);
        String begin = currentDate + TIME_SCOPE_BEGIN;
        String end = currentDate + TIME_SCOPE_END;
        Timestamp beginTime = Timestamp.valueOf(begin);
        Timestamp endTime = Timestamp.valueOf(end);
        return (!time.before(beginTime) && !time.after(endTime));
    }

    public static long betweenTime(String updateTime, String endTime) {
        long upDateTime = parseTime(DATE_FORMAT_SECOND, updateTime).getTime();
        long dateEndTime = parseTime(DATE_FORMAT_SECOND, endTime).getTime();
        long internal = dateEndTime - upDateTime;
        return internal;
    }

    public static Timestamp dateToTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * String-->Timestamp, String的类型必须为：yyyy-MM-dd hh:mm:ss[.f...] 这样的格式，中括号表示可选，否则报错
     *
     * @param s
     * @return
     */
    public static Timestamp stringToTimestamp(String s) {
        return Timestamp.valueOf(s);
    }

    public static String timestampToString(Timestamp timestamp, String pattern) {
        return new SimpleDateFormat(pattern).format(timestamp);
    }


    public static Date formatDateToMinuteByString(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_MINUTE_CHINESE);
        try {
            return sdf.parse(strDate);
        } catch (ParseException e) {
        }
        return null;
    }


    public static Date formatDateToDayByString(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_CHINESE);
        try {
            return sdf.parse(strDate);
        } catch (ParseException e) {
        }
        return null;
    }


    /**
     * @param date
     * @return
     */
    public static String formatDateToMinute(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat(DATE_FORMAT_MINUTE_CHINESE).format(date);
    }

    public static String formatDateToDayByDate(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat(DATE_FORMAT_CHINESE).format(date);
    }

    /**
     * @param s 格式："2014-10-07 11:31:01"
     * @return
     */
    public static String doSubStringByDate(String s) {
        if (s == null)
            return "";
        return s.substring(s.lastIndexOf(" ") + 1, s.lastIndexOf(":"));
    }

    /**
     * @param dateString  需要格式化的日期
     * @param srcFormat   源日期的格式
     * @param todayFormat 当天日期返回的格式
     * @param otherFormat 不是当天日期的格式
     * @return 如果日期是当天，返回todayFormat格式的字符串，否则返回otherFormat格式的字符串
     */
    public static String getDateFromDateString(String dateString, String srcFormat, String todayFormat, String otherFormat) {
        SimpleDateFormat df = new SimpleDateFormat(srcFormat);
        String ds = null;
        try {
            Date date = df.parse(dateString);

            Calendar c = Calendar.getInstance();
            c.setTime(date);
            int m0 = c.get(Calendar.MONTH);
            int d0 = c.get(Calendar.DAY_OF_MONTH);
            //今天的月/日
            Date today = new Date();
            c.setTime(today);
            int m1 = c.get(Calendar.MONTH);
            int d1 = c.get(Calendar.DAY_OF_MONTH);

            boolean isToday = m0 == m1 && d0 == d1;
            if (isToday) {
                ds = new SimpleDateFormat(todayFormat).format(date);
            } else {
                ds = new SimpleDateFormat(otherFormat).format(date);
            }

        } catch (Exception e) {
        }

        return ds;
    }


    public static String formatTime(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat(DATE_FORMAT_TIME_POINT).format(date);
    }

    public static String formatTimeByType(String type, Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat(type).format(date);
    }

    public static Date parseTime(String type, String date) {
        if (date == null)
            return null;
        Date d = null;
        try {
            d = new SimpleDateFormat(type).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public static boolean isToday(Date infoDate) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(infoDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date(System.currentTimeMillis()));
        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);
        return isSameDate;
    }

    public static boolean isToday(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DAY);
        String dateStr = sdf.format(timeStamp);
        String serverDateStr = sdf.format(getTimestampFixed());
        return dateStr != null && dateStr.equals(serverDateStr);
    }

    public static boolean isTodayFix(Date infoDate) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(infoDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date(getTimestampFixed()));
        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);
        return isSameDate;
    }

    public static String long2Date2Str(long curTime, String formatType) {
        int s = 1000;
        int m = 60 * s;
        int h = 60 * m;
        int hour = (int) (curTime / h);
        long mid = curTime % h;
        int min = (int) (mid / m);
        int sec = (int) (mid % m / s);
        String strTime = (min < 10 ? "0" + min : min) + " : " + (sec < 10 ? "0" + sec : sec);
        if (hour > 0) {
            strTime = hour + " : " + strTime;
        } else {
            strTime = "00 : " + strTime;
        }
        return strTime;
    }

    public static String long2DateTime(long curTime, String formatType) {
        Date dateStart = new Date(curTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatType);
        String strTime = simpleDateFormat.format(dateStart);
        return strTime;
    }

    public static String str2DateTime(String mD, String srcFormatType, String formatType) {
        Date dateStart = null;//new Date(mD);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(srcFormatType);
        String strTime = "";//simpleDateFormat.format(dateStart);
        try {
            dateStart = simpleDateFormat.parse(mD);

            SimpleDateFormat sdf = new SimpleDateFormat(formatType);
            strTime = sdf.format(dateStart);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strTime;
    }


    public static String formatTimeH_M(int orgiTime) {
        String t_sTime = String.valueOf(orgiTime);
        return formatTimeH_M(t_sTime);
    }

    // 100646或者70412 转换成 10:06 或者 07:04 string
    public static String formatTimeH_M(String orgTimeStr) {
        String fixTimeString = "";
        String t_str = formatTimeH_M_S(orgTimeStr);
        if (!t_str.equals("")) {
            fixTimeString = t_str.substring(0, 5);
        }

        return fixTimeString;
    }

    // 100646或者70412 转换成 10:06:46 或者 07:04:12
    public static String formatTimeH_M_S(String orgTimeStr) {
        String fixTimeString = "";
        if (orgTimeStr != null) {
            int nlen = orgTimeStr.length();
            if (nlen == 1) {
                fixTimeString = "00:00:0" + orgTimeStr;
            } else if (nlen == 2) {
                fixTimeString = "00:00:" + orgTimeStr;
            } else if (nlen == 3) {
                fixTimeString = "00:0" + orgTimeStr.substring(0, 1) + ":" + orgTimeStr.substring(1, 3);
            } else if (nlen == 4) {
                fixTimeString = "00:" + orgTimeStr.substring(0, 2) + ":" + orgTimeStr.substring(2, 4);
            } else if (nlen == 5) {
                fixTimeString = "0" + orgTimeStr.substring(0, 1) + ":" + orgTimeStr.substring(1, 3) + ":" + orgTimeStr.substring(3, 5);
            } else if (nlen == 6) {
                fixTimeString = orgTimeStr.substring(0, 2) + ":" + orgTimeStr.substring(2, 4) + ":" + orgTimeStr.substring(4, 6);
            }

        }
        return fixTimeString;
    }

    public static String formatDateM_D(String orgDate, String separator) {
        if (TextUtils.isEmpty(orgDate)) {
            return "";
        }

        String fixDate = "";
        String t_separator = "/";
        if (separator != null && !separator.equals("")) {
            t_separator = separator;
        }

        int nlen = orgDate.length();
        if (nlen >= 8) {
            String tMonth = orgDate.substring(4, 6);
            String tDay = orgDate.substring(6, 8);
            fixDate = tMonth + t_separator + tDay;
        }

        return fixDate;
    }

    public static String formatDdmmyyyy(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_FORMAT_DAY_MONTH_YEAR_TIME);
        String strDate = dateFormat.format(date);
        return strDate;
    }

    /**
     * 返回一天中两个时间的相隔的分钟数,不能跨天
     *
     * @param time1 24小时制 930表示9:30;  1550表示15:50
     * @param time2
     * @return
     */
    public static int getTimeGapInOneDay(int time1, int time2) {
        int tHourGap = time1 / 100 - time2 / 100;
        int tMinGap = time1 % 100 - time2 % 100;
        int tGap = tHourGap * 60 + tMinGap;
        return Math.abs(tGap);

    }

    /**
     * 返回服务器格式的当天日期
     *
     * @param timeZone
     * @return yyyyMMdd   例:20170423
     */
    public static final TimeZone BEIJI_TIMEZONE = TimeZone.getTimeZone("GMT+8");

    public static int getServerDayFixed(TimeZone timeZone) {
        long timestamp = getTimestampFixed();
        Date date = new Date(timestamp);

        SimpleDateFormat tFormat = new SimpleDateFormat("yyyyMMdd");
        if (timeZone == null) {
            tFormat.setTimeZone(BEIJI_TIMEZONE);
        } else {
            tFormat.setTimeZone(timeZone);
        }

        String tStr = tFormat.format(date);
        int iDate = DataUtils.convertToInt(tStr);
        return iDate;
    }

    /**
     * 修正后 获取时间戳,精确到毫秒
     *
     * @return
     */
    public static long G_LOCAL_SERVER_TIME_GAP = 0; // 服务器业务时间 和 本地时间的差值(毫秒) gap = st - lt

    public static long getTimestampFixed() {
        return System.currentTimeMillis() + G_LOCAL_SERVER_TIME_GAP;
    }

    public static TimeZone getTimeZone(int timeZoneGap) {
        String gmtFlag = timeZoneGap >= 0 ? "+" : "-";
        DecimalFormat mDecimalFormat = new DecimalFormat("00");
        String gmtH = mDecimalFormat.format(Math.abs(timeZoneGap) / 60);
        String gmtM = mDecimalFormat.format(Math.abs(timeZoneGap) % 60);

        return TimeZone.getTimeZone("GMT" + gmtFlag + gmtH + ":" + gmtM);
    }

    /**
     * 计算时间增加
     *
     * @param orgTime 基础时间,格式:00:20->20, 09:30->930, 10:20->1020
     * @param add     增加的分钟数 50表示往后分钟
     * @return 10:30->1030;  -1表示错误
     */
    public static int timeAdd(int orgTime, int add) {
        int tMin = orgTime / 100 * 60 + orgTime % 100;
        while (tMin + add < 0) {
            tMin += 24 * 60;
        }

        tMin += add;
        int hour = tMin / 60 % 24;
        int minute = tMin % 60;
        int retTime = hour * 100 + minute;
        return retTime;
    }

    public static SimpleDateFormat mFormatDD = new SimpleDateFormat("dd");
    public static SimpleDateFormat mFormatMM = new SimpleDateFormat("MM");
    public static String formatInfoDate(long timeStamp, SimpleDateFormat format) {
        if (timeStamp == 0) {
            return "";
        }
        Date date = new Date(timeStamp);
        return format.format(date);
    }

    public static String formatInfoDate(long timeStamp,String format){
        return formatInfoDate(timeStamp,new SimpleDateFormat(format));
    }



    /**
     * 获取年月日的月份
     * long->八月
     *
     * @return
     */
    public static String getMonthOfDate(long time) {
        String strMonth = formatInfoDate(time, mFormatMM);
        if(TextUtils.isEmpty(strMonth))
        {
            return "";
        }
        int month = Integer.valueOf(strMonth);
        String[] monthArr = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
        if (month > 0 && month <= 12) {
            return monthArr[month - 1];
        } else {
            return "";
        }
    }
    public static SimpleDateFormat mFormatDay = new SimpleDateFormat("yyyyMMdd");


    public static Calendar getServerDateFixed(TimeZone timeZone) {
        if (timeZone == null) {
            timeZone = BEIJI_TIMEZONE;
        }

        long timestamp = getTimestampFixed();
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTimeInMillis(timestamp);
        return calendar;

    }
}
