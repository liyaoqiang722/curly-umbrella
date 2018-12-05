package com.wj.spc.demo_1203.commons.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * 日期相关工具
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    /**
     * GMT日期格式
     */
    public static final String GMT_DATE_FORMAT = "EEE MMM dd yyyy HH:mm:ss zzz";

    /**
     * yyyy年MM月dd日 HH:mm:ss
     */
    public static final String CHINESE_TIME_FORMAT = "yyyy年MM月dd日 HH:mm:ss";

    /**
     * yyyy年MM月dd日 HH:mm:ss
     */
    public static final String CHINESE_DATE_FORMAT = "yyyy年MM月dd日";
    /**
     * yyyy年MM月dd日 HH:mm
     */
    public static final String CHINESE_TIME_FORMAT_1 = "yyyy年MM月dd日 HH:mm";
    /**
     * yyyy.MM.dd HH:mm:ss
     */
    public static final String DEFAULT_TIME_FORMAT_2 = "yyyy.MM.dd HH:mm:ss";
    /**
     * YYYYMMddHHmmss
     */
    public static final String DEFAULT_TIME_FORMAT_3 = "yyyyMMddHHmmss";
    /**
     * yyyy-MM-dd HH:mm:ss：SSS
     */
    public static final String DEFAULT_TIME_FORMAT_4 = "yyyyMMddHHmmssSSS";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String DEFAULT_TIME_FORMAT_1 = "yyyy-MM-dd HH:mm";
    /**
     * yyyy.MM.dd
     */
    public static final String YEAR_MONTH_DAY_FORMAT_2 = "yyyy.MM.dd";
    /**
     * yyyyMMdd
     */
    public static final String YEAR_MONTH_DAY_FORMAT_3 = "yyyyMMdd";
    /**
     * yyyy-MM-dd
     */
    public static final String YEAR_MONTH_DAY_FORMAT = "yyyy-MM-dd";
    /**
     * MM-dd
     */
    public static final String HOUR_MINUTE_FORMAT = "MM-dd";
    /**
     * MM-dd
     */
    public static final String MONTH_DAY_FORMAT = "MM-dd";
    /**
     * 时间格式
     */
    public static final String YYYY_MM_dd_T_HH_mm_ss_SSS_Z = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    /**
     * 时间格式
     */
    public static final String YYYY_MM_dd_T_HH_mm_ss = "yyyy-MM-dd'T'HH:mm:ss";
    /**
     * 时间格式
     */
    public static final String YYYY_MM_dd_HH_mm = "yyyy/MM/dd HH:mm";

    public static final String YYYY_MM_dd = "yyyy/MM/dd";

    public static final String YYYY_MM_dd_T_HH_mm = "yyyy-MM-dd'T'HH:mm";

    /**
     * 获取日期的缩写(PM/AM)
     * 12点或12点之后返回PM,否则AM
     *
     * @param date
     * @return
     */
    public static String getTimeCode(Date date) {
        if (date == null) {
            return null;
        }
        if (getHour(date) > 11) {
            return "PM";
        } else {
            return "AM";
        }
    }

    /**
     * 两个日期比较
     *
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean compareDate(Object obj1, Object obj2) {
        if (obj1 == null || obj2 == null) {
            return false;
        } else {
            Date date1 = StringToDate(obj1, "yyyy-MM-dd");
            Date date2 = StringToDate(obj2, "yyyy-MM-dd");
            if (date1 != null && date1.after(date2)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static int compareTo(Date date, Date anotherDate) {
        if (date != null && anotherDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Calendar anotherCalendar = Calendar.getInstance();
            anotherCalendar.setTime(anotherDate);
            return calendar.compareTo(anotherCalendar);
        }
        return -1;
    }

    /**
     * 时间转换String
     *
     * @param date
     * @param format
     * @return
     */
    public static String dateToString(Date date, String format) {
        if (date == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * String 转换Date
     *
     * @param obj
     * @param format 24小时：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date StringToDate(Object obj, String format) {
        if (obj == null)
            return null;
        String date = obj.toString().trim();
        if (StringUtils.isEmpty(date))
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            LOGGER.error("Date Transform Exception: StringToDate", e);
            return null;
        }
    }

    /**
     * String 转换Date 如果String 空 默认取当前时间
     *
     * @param obj
     * @param format
     * @return
     */
    public static Date StringToDate2_NewDate(Object obj, String format) {
        if (obj == null)
            return new Date();
        String date = obj.toString().trim();
        if (StringUtils.isEmpty(date))
            return new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {

            return sdf.parse(date.trim());
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * 获取当前时间,请注意format 24小时制 HH:mm:ss
     *
     * @param format
     * @return
     */
    public static String getCurrentTimeMillis(String format) {
        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat(format);
        String retStrFormatNowDate = sdFormatter.format(nowTime);
        return retStrFormatNowDate;
    }

    /**
     * 获取当前时间,默认 24小时制 HH:mm:ss
     *
     * @return
     */
    public static String getCurrentTimeMillis() {
        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String retStrFormatNowDate = sdFormatter.format(nowTime);
        return retStrFormatNowDate;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getCurrentTime() {
        Date nowTime = new Date(System.currentTimeMillis());
        return nowTime;
    }

    /**
     * 获取当前时间的字符串
     *
     * @param dateFormat
     * @return
     */
    public static String getCurrentTimeStr(String dateFormat) {
        Date currentDate = getCurrentTime();
        return dateToString(currentDate, dateFormat);
    }

    /**
     * 把时间化为秒 HH:MM:SS
     *
     * @param obj
     * @return
     */
    public static int DateToSecond(Object obj) {
        if (obj == null)
            return 0;
        String date = obj.toString().trim();
        if (StringUtils.isEmpty(date))
            return 0;
        int hour = Integer.valueOf(date.split(":")[0]);
        int minute = Integer.valueOf(date.split(":")[1]);
        int second = Integer.valueOf(date.split(":")[2]);
        return hour * 60 * 60 + minute * 60 + second;
    }

    /**
     * 取得两个日期相差的天数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int getDayMargin(Date beginDate, Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 1, 0, 0);
        long beginTime = calendar.getTime().getTime();
        calendar.setTime(endDate);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 1, 0, 0);
        long endTime = calendar.getTime().getTime();
        return (int) Math.ceil(((double) (endTime - beginTime) / (1000 * 60 * 60 * 24)));
    }

    /**
     * 获取年份间隔
     *
     * @param beginYear
     * @param endYear
     * @return
     */
    public static int getYearMargin(int beginYear, int endYear) {
        return endYear - beginYear;
    }

    /**
     * 计算第二个时间与第一个时间相差小时数
     *
     * @param beginDate
     * @param endDate
     * @return
     */

    public static int getHoursMargin(Date beginDate, Date endDate) {
        long result = (endDate.getTime() / (60 * 60 * 1000)) - (beginDate.getTime() / (60 * 60 * 1000));
        return (int) result;
    }

    /**
     * 计算第二个时间与第一个时间相差分钟数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int getMinuteMargin(Date beginDate, Date endDate) {
        long result = (endDate.getTime() / (60 * 1000)) - (beginDate.getTime() / (60 * 1000));
        return (int) result;
    }

    /**
     * 判断一个时间是否在某一个时间段内
     *
     * @param targetDate 目标时间
     * @param beginDate  时间段的开始时间
     * @param endDate    时间段的结束时间
     * @return
     */
    public static boolean isBetween(Date targetDate, Date beginDate, Date endDate) {
        boolean temp = false;
        long target = targetDate.getTime();
        long begin = beginDate.getTime();
        long end = endDate.getTime();
        if (target >= begin && target <= end) {// 需要考虑在考勤中 时间 分钟以后的秒跟毫秒忽略
            temp = true;
        }
        return temp;
    }

    /**
     * 判断一个时间是否在另外两个时间分别加上几分钟后的时间段内
     *
     * @param targetDate 目标时间
     * @param beginDate  开始时间
     * @param endDate    结束时间
     * @param start      开始分钟数
     * @param after      结束分钟数
     * @return
     */
    public static boolean isBetween(Date targetDate, Date beginDate, Date endDate, int start, int after) {
        boolean temp = false;
        long target = targetDate.getTime();
        long begin = beginDate.getTime() + start * 60 * 1000;
        long end = endDate.getTime() + after * 60 * 1000;
        if (target >= begin && target <= end) {// 需要考虑在考勤中 时间 分钟以后的秒跟毫秒忽略
            temp = true;
        }
        return temp;
    }

    /**
     * 判断两个日期是否为同一天
     *
     * @param d1 日期一
     * @param d2 日期二
     * @return 同一天true，不是同一天false
     */
    public static boolean isSameDay(Date d1, Date d2) {
        boolean result = false;
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)) {
            result = true;
        }
        return result;
    }

    /**
     * 根据分钟进行判断
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isSameTime(Date d1, Date d2) {
        boolean result = false;
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH) && c1.get(Calendar.HOUR_OF_DAY) == c2.get(Calendar.HOUR_OF_DAY) && c1.get(Calendar.MINUTE) == c2.get(Calendar.MINUTE)) {
            result = true;
        }
        return result;
    }

    public static String getHourMinute(Date date) {

        return new SimpleDateFormat("HH:mm").format(date);
    }

    /**
     * 获取时间的小时
     *
     * @param date
     * @return
     */
    public static Integer getHour(Date date) {
        if (date == null) {
            return null;
        }
        return Integer.parseInt(new SimpleDateFormat("HH").format(date));
    }

    /**
     * 取得一个年月日组合的长度为8的字符串
     *
     * @param date
     * @return
     */
    public static String getYMD(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String id = calendar.get(Calendar.YEAR) + isAddZero(calendar.get(Calendar.MONTH) + 1) + isAddZero(calendar.get(Calendar.DAY_OF_MONTH));
        return id;
    }

    public static String isAddZero(int a) {
        String s = "";
        if (a < 10) {
            s = "0" + a;
        } else {
            s += a;
        }
        return s;

    }

    /**
     * 取得一个时间所在月的第一天
     *
     * @param date
     * @return
     */
    public static String getCurrentMondthOne(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-01";
    }

    /**
     * 根据一个标准的字符串获得一个时间
     *
     * @param str
     * @return
     */
    public static Date getDateByStr(String str) {
        try {
            return new SimpleDateFormat(DEFAULT_TIME_FORMAT).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前年
     *
     * @return
     */
    public static int getCurrentYear() {
        Date d = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int year = c.get(Calendar.YEAR);
        return year;
    }

    /**
     * 获取当前月
     *
     * @return
     * @throws ParseException
     */
    public static int getCurrentMonth() throws ParseException {
        Date d = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        // int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        // int day = c.get(Calendar.DAY_OF_MONTH);
        return month;
    }

    public static Date getDateByStr(String str, String format) {
        try {
            return new SimpleDateFormat(format).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Timestamp getTimestampByStr(String str, String format) {
        try {
            Date date = new SimpleDateFormat(format).parse(str);
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
        }
        return null;
    }

    public static String getCrruentTime() {
        return new SimpleDateFormat(DEFAULT_TIME_FORMAT).format(new Date());
    }

    public static String getCrruentTime(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String getCrruentTime(String format, Date d) {
        return new SimpleDateFormat(format).format(d);
    }

    public static int getWeekToday() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        return dayOfWeek - 1;
    }

    public static Timestamp getCrruentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String getTime(Timestamp timestamp) {
        return new SimpleDateFormat(DEFAULT_TIME_FORMAT).format(timestamp);
    }

    public static String getTime(Timestamp timestamp, String format) {
        return new SimpleDateFormat(format).format(timestamp);
    }

    /**
     * 给当期指定的日期加上指定的天数
     *
     * @param now 指定的日期
     * @param day 添加的天数
     * @return
     */
    public static Date getDateByAddDay(Date now, int day) {
        return setDate(now, Calendar.DAY_OF_MONTH, day);
    }

    /**
     * 给当期指定的日期加上指定的月
     *
     * @param now   指定的日期
     * @param month 添加的月份数
     * @return
     */
    public static Date getDateByAddMonth(Date now, int month) {
        return setDate(now, Calendar.MONTH, month);
    }

    /**
     * 给当期指定的日期加上指定的月份
     *
     * @param now   指定的日期
     * @param field 日期字段：YEAR，MONTH， DAY_OF_MONTH WEEK_OF_MONTH， DAY_OF_WEEK，
     *              DAY_OF_WEEK_IN_MONTH， WEEK_OF_YEAR 时间字段：HOUR_OF_DAY AM_PM +
     *              HOUR
     * @param value 加减的量制
     * @return
     */
    private static Date setDate(Date now, int field, int value) {
        if (now == null)
            return null;
        Calendar cad = Calendar.getInstance();
        cad.setTime(now);

        cad.add(Calendar.DAY_OF_MONTH, value);
        return cad.getTime();
    }

    public static Date getDateByCalcHours(Date date, int hours) {
        return setDateNew(date, Calendar.HOUR_OF_DAY, hours);
    }

    public static Date getDateByCalcMinutes(Date date, int minutes) {
        return setDateNew(date, Calendar.MINUTE, minutes);
    }

    private static Date setDateNew(Date now, int calendarField, int value) {
        if (now == null)
            return null;
        Calendar cad = Calendar.getInstance();
        cad.setTime(now);

        cad.add(calendarField, value);
        return cad.getTime();
    }

    /**
     * 日期加减
     *
     * @param now
     * @param value 正数为加 负数为减
     * @return
     */
    public static String addAndMinusDate(String now, int value) {
        Calendar calendar = Calendar.getInstance();
        Date nowDate = StringToDate(now, "yyyy-MM-dd HH:mm:ss");
        calendar.setTime(nowDate);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + value);
        return dateToString(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 当前时间加减分钟数
     *
     * @param value
     * @return
     */
    public static String addAndMinusMinute(int value) {
        Calendar calendar = Calendar.getInstance();
        Date nowDate = getCurrentTime();
        calendar.setTime(nowDate);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + value);
        return dateToString(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 时间加减操作
     * DateUtil.calendarAddOrMinus
     *
     * @param date
     * @param field Calendar常量值
     * @param value 正数加  负数减
     * @return
     * @author: luhm
     * @date 2015年11月11日
     */
    public static Date calendarAddOrMinus(Date date, int field, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(field, calendar.get(field) + value);
        return calendar.getTime();
    }

    public static String getTimeFormatBegin(int page, int size) {

        Calendar calendar = Calendar.getInstance();
        Date nowDate = getCurrentTime();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DAY_OF_MONTH, -page * size);

        return dateToString(calendar.getTime(), "yyyy-MM") + "-01";
    }

    public static String getTimeFormatEnd(int page, int size) {

        Calendar calendar = Calendar.getInstance();
        Date nowDate = getCurrentTime();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DAY_OF_MONTH, -page * size);

        return dateToString(calendar.getTime(), "yyyy-MM-dd");
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static String getValidatyTime(int day) {
        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();

        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, day);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
        String defaultStartDate = sdf.format(dBefore);    //格式化前一天

        return defaultStartDate;
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"3", "2", "2", "2", "2", "2", "3"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        String week = weekDays[w];
        return week;
    }


    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDateTime(Date dt) {
        String[] weekDays = {"0", "1", "2", "3", "4", "5", "6"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        String week = weekDays[w];
        return week;
    }

    /**
     * 获取日期的星期字符串：星期X
     * DateUtil.getWeek()<BR>
     * <P>Author :  liuxh </P>
     * <P>Date : 2015年11月7日 </P>
     *
     * @param date
     * @return
     */
    public static String getWeek(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        String week = weekDays[w];
        return week;
    }

    public static Date formatDate(Date date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String tempStr = formatter.format(date);
        try {
            return formatter.parse(tempStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将分钟转换为"X小时X分钟"
     *
     * @param minute 分钟数
     * @return
     */
    public static String minute2HMDes(int minute) {
        if (minute == 0) {
            return "";
        }
        int hour = minute / 60;
        int leftMinute = minute % 60;
        StringBuffer buffer = new StringBuffer();
        buffer.append(hour + "小时")
                .append(leftMinute == 0 ? "" : leftMinute + "分钟");
        return buffer.toString();
    }

    /**
     * 获取前一个日期的年月日，后一个日期的时分秒，然后返回时间
     *
     * @param date
     * @param time
     * @return
     */
    public static Date getDate(Date date, Date time) {

        Calendar cadDate = Calendar.getInstance();
        Calendar cadTime = Calendar.getInstance();
        cadDate.setTime(date);
        cadTime.setTime(time);
        Calendar returnTime = Calendar.getInstance();
        returnTime.set(cadDate.get(Calendar.YEAR), cadDate.get(Calendar.MONTH), cadDate.get(Calendar.DAY_OF_MONTH), cadTime.get(Calendar.HOUR_OF_DAY), cadTime.get(Calendar.MINUTE), cadTime.get(Calendar.SECOND));
        return returnTime.getTime();
    }

    /**
     * 转化GMT格式日期
     *
     * @param GMTTimeStr
     * @param newDateFormat
     * @return
     * @throws RuntimeException 当参数不准确,或者是转化出现错误时会抛出此异常
     */
    public static Date parseGMTDate(String GMTTimeStr, String newDateFormat) throws RuntimeException {
        if (StringUtils.isEmpty(GMTTimeStr) || StringUtils.isEmpty(newDateFormat)) {
            throw new RuntimeException("参数不完整!");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(GMT_DATE_FORMAT, Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date;
        try {
            date = sdf.parse(GMTTimeStr);
        } catch (ParseException e) {
            throw new RuntimeException("格式化日期时出现异常", e);
        }
        return date;
    }

    /**
     * 将unix类型的时间转化为date格式
     *
     * @param timestampString
     * @return
     */
    public static Date timeStamptoDate(String timestampString) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        return new Date(timestamp);
    }


    /**
     * 返回unix时间戳 (1970年至今的秒数)
     *
     * @return
     */
    public static long getUnixStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 得到昨天的日期
     *
     * @return
     */
    public static String getYestoryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String yestoday = sdf.format(calendar.getTime());
        return yestoday;
    }

    /**
     * 得到今天的日期
     *
     * @return
     */
    public static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 时间戳转化为时间格式
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToStr(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }

    /**
     * 得到日期   yyyy-MM-dd
     *
     * @param timeStamp 时间戳
     * @return
     */
    public static String formatDate(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }

    /**
     * 得到时间  HH:mm:ss
     *
     * @param timeStamp 时间戳
     * @return
     */
    public static String getTime(long timeStamp) {
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(timeStamp * 1000);
        String[] split = date.split("\\s");
        if (split.length > 1) {
            time = split[1];
        }
        return time;
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp
     * @return
     */
    public static String convertTimeToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;

        if (time < 60 && time >= 0) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            return time / 3600 / 24 + "天前";
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 + "个月前";
        } else if (time >= 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 / 12 + "年前";
        } else {
            return "刚刚";
        }
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，(多少分钟)
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;
        return time / 60 + "";
    }

    public static String getDefaultDateTime(Date date) {
        return new SimpleDateFormat(DEFAULT_TIME_FORMAT).format(date);
    }

    /**
     * 取得当日开始时间
     *
     * @return
     */
    public static Date getDayStart(String str) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH_DAY_FORMAT);
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(sdf.parse(str));
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);

        return todayStart.getTime();
    }


    /**
     * 取得当日开始时间
     *
     * @return
     */
    public static Date getDayStart(Date day) {
        Calendar todayStart = Calendar.getInstance();

        todayStart.setTime(day);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);

        return todayStart.getTime();
    }

    /**
     * 取得当日结束时间
     *
     * @return
     */
    public static Date getDayEnd(Date day) {
        Calendar todayEnd = Calendar.getInstance();

        todayEnd.setTime(day);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);

        return todayEnd.getTime();
    }

    /**
     * 取得当日开始时间
     *
     * @return
     */
    public static Date getTodayStart() {
        Calendar todayStart = Calendar.getInstance();

        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);

        return todayStart.getTime();
    }

    /**
     * 取得当日结束时间
     *
     * @return
     */
    public static Date getTodayEnd() {
        Calendar todayEnd = Calendar.getInstance();

        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);

        return todayEnd.getTime();
    }

    /**
     * 取得当周开始时间
     *
     * @return
     */
    public static Date getWeekStart() {
        Calendar cal = Calendar.getInstance();

        cal.setTime(new Date());
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 取得当周开始时间
     *
     * @return
     */
    public static Date getWeekEnd() {
        Calendar cal = Calendar.getInstance();

        cal.setTime(getWeekStart());
        cal.add(Calendar.DAY_OF_WEEK, 6);

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        return cal.getTime();
    }


    /**
     * 取得上周开始时间
     *
     * @return
     */
    public static Date getPreWeekStart() {
        Calendar weekStart = Calendar.getInstance();
        weekStart.setTime(addWeeks(getWeekStart(), -1));

        return weekStart.getTime();
    }

    /**
     * 取得上周结束时间
     *
     * @return
     */
    public static Date getPreWeekEnd() {
        Calendar weekEnd = Calendar.getInstance();
        weekEnd.setTime(addWeeks(getWeekEnd(), -1));

        return weekEnd.getTime();
    }

    /**
     * 取得当月开始时间
     *
     * @return
     */
    public static Date getMonthStart() {
        Calendar monthStart = Calendar.getInstance();

        monthStart.set(monthStart.get(Calendar.YEAR), monthStart.get(Calendar.MONDAY), monthStart.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

        monthStart.add(Calendar.MONTH, 0);

        monthStart.set(Calendar.DAY_OF_MONTH, 1);
        monthStart.set(Calendar.MILLISECOND, 0);

        return monthStart.getTime();
    }

    /**
     * 取得当月开始时间
     *
     * @return
     */
    public static Date getMonthEnd() {
        Calendar monthEnd = Calendar.getInstance();

        monthEnd.set(monthEnd.get(Calendar.YEAR), monthEnd.get(Calendar.MONDAY), monthEnd.get(Calendar.DAY_OF_MONTH), 23, 59, 59);

        monthEnd.add(Calendar.MONTH, 1);

        monthEnd.set(Calendar.DAY_OF_MONTH, 0);
        monthEnd.set(Calendar.MILLISECOND, 999);

        return monthEnd.getTime();
    }

    /**
     * 取得昨日开始时间
     *
     * @return
     */
    public static Date getYesterdayStart() {
        Calendar todayStart = Calendar.getInstance();

        todayStart.add(todayStart.DATE, -1);

        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);

        return todayStart.getTime();
    }

    /**
     * 取得昨日结束时间
     *
     * @return
     */
    public static Date getYesterdayEnd() {
        Calendar todayEnd = Calendar.getInstance();

        todayEnd.add(todayEnd.DATE, -1);

        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);

        return todayEnd.getTime();
    }

    /**
     * 某一时间加分钟数
     *
     * @return
     */
    public static Date addMinutes(Date date, int minutes) {

        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, minutes);

        return nowTime.getTime();
    }

    /**
     * 某一时间加月数
     *
     * @return
     */
    public static Date addMounth(Date date, int mounth) {

        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MONTH, mounth);

        return nowTime.getTime();
    }
}
