package com.cyan.myday.sql.tables;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;


public class CalendarDateBean {
//    protected static Log logger = Log.d(CalendarDateBean.class);

    // ��ʽ���꣭�£��� Сʱ�����ӣ���
    public static final String FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";

    // ��ʽ���꣭�£��� Сʱ������
    public static final String FORMAT_TWO = "yyyy-MM-dd HH:mm";

    // ��ʽ�������� Сʱ������
    public static final String FORMAT_THREE = "yyyyMMdd-HHmmss";

    // ��ʽ���꣭�£���
    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";

    // ��ʽ���£���
    public static final String SHORT_DATE_FORMAT = "MM-dd";

    // ��ʽ��Сʱ�����ӣ���
    public static final String LONG_TIME_FORMAT = "HH:mm:ss";

    //��ʽ����-��
    public static final String MONTG_DATE_FORMAT = "yyyy-MM";

    // ��ļӼ�
    public static final int SUB_YEAR = Calendar.YEAR;

    // �¼Ӽ�
    public static final int SUB_MONTH = Calendar.MONTH;

    // ��ļӼ�
    public static final int SUB_DAY = Calendar.DATE;

    // Сʱ�ļӼ�
    public static final int SUB_HOUR = Calendar.HOUR;

    // ���ӵļӼ�
    public static final int SUB_MINUTE = Calendar.MINUTE;

    // ��ļӼ�
    public static final int SUB_SECOND = Calendar.SECOND;

    static final String dayNames[] = { "������", "����һ", "���ڶ�", "������", "������",
            "������", "������" };

    @SuppressWarnings("unused")
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");


    /**
     * �ѷ������ڸ�ʽ���ַ���ת��Ϊ��������
     */
    public static java.util.Date stringtoDate(String dateStr, String format) {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr);
        } catch (Exception e) {
            // log.error(e);
            d = null;
        }
        return d;
    }

    /**
     * �ѷ������ڸ�ʽ���ַ���ת��Ϊ��������
     */
    public static java.util.Date stringtoDate(String dateStr, String format,
            ParsePosition pos) {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr, pos);
        } catch (Exception e) {
            d = null;
        }
        return d;
    }

    /**
     * ������ת��Ϊ�ַ���
     */
    public static String dateToString(java.util.Date date, String format) {
        String result = "";
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            result = formater.format(date);
        } catch (Exception e) {
            // log.error(e);
        }
        return result;
    }

    /**
     * ��ȡ��ǰʱ���ָ����ʽ
     */
    public static String getCurrDate(String format) {
        return dateToString(new Date(), format);
    }

    public static String dateSub(int dateKind, String dateStr, int amount) {
        Date date = stringtoDate(dateStr, FORMAT_ONE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(dateKind, amount);
        return dateToString(calendar.getTime(), FORMAT_ONE);
    }

    /**
     * �����������
     * @return ����õ�������
     */
    public static long timeSub(String firstTime, String secTime) {
        long first = stringtoDate(firstTime, FORMAT_ONE).getTime();
        long second = stringtoDate(secTime, FORMAT_ONE).getTime();
        return (second - first) / 1000;
    }

    /**
     * ���ĳ�µ�����
     */
    public static int getDaysOfMonth(String year, String month) {
        int days = 0;
        if (month.equals("1") || month.equals("3") || month.equals("5")
                || month.equals("7") || month.equals("8") || month.equals("10")
                || month.equals("12")) {
            days = 31;
        } else if (month.equals("4") || month.equals("6") || month.equals("9")
                || month.equals("11")) {
            days = 30;
        } else {
            if ((Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0)
                    || Integer.parseInt(year) % 400 == 0) {
                days = 29;
            } else {
                days = 28;
            }
        }

        return days;
    }

    /**
     * ��ȡĳ��ĳ�µ�����
     */
    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * ��õ�ǰ����
     */
    public static int getToday() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    /**
     * ��õ�ǰ�·�
     */
    public static int getToMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * ��õ�ǰ���
     */
    public static int getToYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * �������ڵ���
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }
    /**
     * 	����Сʱ
     */
    public static int getHour(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    
    /**
     * 	���ط���
     */
    public static int getMin(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }
    
    /**
     * �������ڵ���
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * �������ڵ��·ݣ�1-12
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * �������������������������date2 > date1 �������������򷵻ظ���
     */
    public static long dayDiff(Date date1, Date date2) {
        return (date2.getTime() - date1.getTime()) / 86400000;
    }

    /**
     * �Ƚ��������ڵ����
     */
    public static int yearDiff(String before, String after) {
        Date beforeDay = stringtoDate(before, LONG_DATE_FORMAT);
        Date afterDay = stringtoDate(after, LONG_DATE_FORMAT);
        return getYear(afterDay) - getYear(beforeDay);
    }

    /**
     * �Ƚ�ָ�������뵱ǰ���ڵĲ�
     */
    public static int yearDiffCurr(String after) {
        Date beforeDay = new Date();
        Date afterDay = stringtoDate(after, LONG_DATE_FORMAT);
        return getYear(beforeDay) - getYear(afterDay);
    }
    
    /**
     * �Ƚ�ָ�������뵱ǰ���ڵĲ�
     */
//    public static long dayDiffCurr(String before) {
//        Date currDate = DateUtil.stringtoDate(currDay(), LONG_DATE_FORMAT);
//        Date beforeDate = stringtoDate(before, LONG_DATE_FORMAT);
//        return (currDate.getTime() - beforeDate.getTime()) / 86400000;
//
//    }

    /**
     * ��ȡÿ�µĵ�һ��
     */
    public static int getFirstWeekdayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SATURDAY); // ������Ϊ��һ��
        c.set(year, month - 1, 1);
        return c.get(Calendar.DAY_OF_WEEK);
    }
    /**
     * ��ȡÿ�µ����һ��
     */
    public static int getLastWeekdayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SATURDAY); // ������Ϊ��һ��
        c.set(year, month - 1, getDaysOfMonth(year, month));
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * ��õ�ǰ�����ַ�������ʽ"yyyy_MM_dd_HH_mm_ss"
     * 
     * @return
     */
//    public static String getCurrent() {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        int year = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH) + 1;
//        int day = cal.get(Calendar.DAY_OF_MONTH);
//        int hour = cal.get(Calendar.HOUR_OF_DAY);
//        int minute = cal.get(Calendar.MINUTE);
//        int second = cal.get(Calendar.SECOND);
//        StringBuffer sb = new StringBuffer();
//        sb.append(year).append("_").append(StringUtil.addzero(month, 2))
//                .append("_").append(StringUtil.addzero(day, 2)).append("_")
//                .append(StringUtil.addzero(hour, 2)).append("_").append(
//                        StringUtil.addzero(minute, 2)).append("_").append(
//                        StringUtil.addzero(second, 2));
//        return sb.toString();
//    }

    /**
     * ��õ�ǰ�����ַ�������ʽ"yyyy-MM-dd HH:mm:ss"
     * 
     * @return
     */
    public static String getNow() {
        Calendar today = Calendar.getInstance();
        return dateToString(today.getTime(), FORMAT_ONE);
    }

/**
     * �ж������Ƿ���Ч,������������
     * 
     * @param date
     *          YYYY-mm-dd
     * @return
     */
    public static boolean isDate(String date) {
        StringBuffer reg = new StringBuffer(
                "^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
        reg.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
        reg.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
        reg.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
        reg.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
        reg.append("1-9])|(1[0-9])|(2[0-8]))))))");
        Pattern p = Pattern.compile(reg.toString());
        return p.matcher(date).matches();
    }
}