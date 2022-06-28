package cn.tenyear.blog.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
public final class DateUtil {
    /**
     * 一秒
     */
    private static final long SECOND = 1000L;
    /**
     * 一分钟
     */
    private static final long MINUTE = SECOND * 60L;
    /**
     * 一小时
     */
    private static final long HOUR = MINUTE * 60L;
    /**
     * 一天
     */
    private static final long Day = HOUR * 24L;

    private DateUtil() {
    }

    /**
     * 内部调用 System.currentTimeMillis()
     * @return 当前时间与 UTC 1970 年 1 月 1 日午夜之间的差异，以毫秒为单位。
     */
    public static long now() {
        return System.currentTimeMillis();
    }

    /**
     * 默认的日期格式化方式
     * @return 当前日期以 yyyy-MM-dd HH:mm:ss 形式的字符串
     */
    public static String format() {
        return format(Pattern.DEFAULT_DATE_TIME.value, new Date());
    }

    public static String format(String pattern) {
        return format(pattern, new Date());
    }

    public static String format(Pattern pattern) {
        return format(pattern.value, new Date());
    }

    public static String format(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String format(Pattern pattern, Date date) {
        return new SimpleDateFormat(pattern.value).format(date);
    }

    public static String format(Date date) {
        return format(Pattern.DEFAULT_DATE_TIME.value, date);
    }


    public static Date addSeconds(long num) {
        long time = now();
        time += SECOND * num;
        return new Date(time);
    }

    public static Date addSeconds(Date date, long num) {
        long time = date.getTime();
        time += SECOND * num;
        return new Date(time);
    }


    public static Date addMinutes(long num) {
        long time = now();
        time += MINUTE * num;
        return new Date(time);
    }

    public static Date addMinutes(Date date, long num) {
        long time = date.getTime();
        time += MINUTE * num;
        return new Date(time);
    }


    public static Date addHours(long num) {
        long time = now();
        time += HOUR * num;
        return new Date(time);
    }

    public static Date addHours(Date date, long num) {
        long time = date.getTime();
        time += HOUR * num;
        return new Date(time);
    }


    public static Date addDays(long num) {
        long time = now();
        time += Day * num;
        return new Date(time);
    }

    public static Date addDays(Date date, long num) {
        long time = date.getTime();
        time += Day * num;
        return new Date(time);
    }

    public static Date reduceSeconds(long num) {
        long time = now();
        time -= SECOND * num;
        return new Date(time);
    }

    public static Date reduceSeconds(Date date, long num) {
        long time = date.getTime();
        time -= SECOND * num;
        return new Date(time);
    }


    public static Date reduceMinutes(long num) {
        long time = now();
        time -= MINUTE * num;
        return new Date(time);
    }

    public static Date reduceMinutes(Date date, long num) {
        long time = date.getTime();
        time -= MINUTE * num;
        return new Date(time);
    }


    public static Date reduceHours(long num) {
        long time = now();
        time -= HOUR * num;
        return new Date(time);
    }

    public static Date reduceHours(Date date, long num) {
        long time = date.getTime();
        time -= HOUR * num;
        return new Date(time);
    }

    public static String validatedDateTime(String time, Pattern pattern, boolean exact) {
        return validatedDateTime(time, pattern, exact, false);
    }

    public static String validatedDateTime(String time, Pattern pattern) {
        return validatedDateTime(time, pattern, true);
    }

    /**
     * 验证日期
     *
     * @param time           原日期
     * @param pattern        日期格式
     * @param exact          完全相同，equals 比较
     * @param useSeparator   是否返回以分隔符形式的日期(无中文字符)
     * @return 验证成功则返回格式(Pattern)化后的日期，验证失败则返回 null
     */
    public static String validatedDateTime(String time, Pattern pattern, boolean exact, boolean useSeparator) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern.value);
            Date date = simpleDateFormat.parse(time);

            String s = format(pattern, date);
            if (useSeparator) {
                s = s.replace("年", "-").replace("月", "-").replace("日", "").replace("时", ":").replace("分", ":").replace("秒", "");
            }
            System.out.println(s);
            if (exact) {
                return s.equals(time) ? s : null;
            } else {
                return s;
            }
        } catch (ParseException e) {
            return null;
        }
    }

    public enum Pattern {
        /**
         * yyyy-MM-dd HH:mm:ss
         */
        DEFAULT_DATE_TIME("yyyy-MM-dd HH:mm:ss"),
        /**
         * yyyy年MM月dd日 HH时mm分ss秒
         */
        CHINESE_DATE_TIME("yyyy年MM月dd日 HH时mm分ss秒"),
        /**
         * yyyy-MM-dd
         */
        DEFAULT_DATE("yyyy-MM-dd"),
        /**
         * yyyy年MM月dd日
         */
        CHINESE_DATE("yyyy年MM月dd日"),
        /**
         * HH:mm:ss
         */
        DEFAULT_TIME("HH:mm:ss"),
        /**
         * HH时mm分ss秒
         */
        CHINESE_TIME("HH时mm分ss秒"),
        ;
        private final String value;

        Pattern(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
