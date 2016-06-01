package com.keke.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public final class DateUtil {
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

	private static final DateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	public static DateFormat getDateFormat() {
		return DATE_FORMAT;
	}

	public static DateFormat getTimeFormat() {
		return TIME_FORMAT;
	}

	public static DateFormat getDatetimeFormat() {
		return DATETIME_FORMAT;
	}

	public static DateFormat getTimestampFormat() {
		return TIMESTAMP_FORMAT;
	}

	public static java.util.Date getCurrentUtilDate() {
		return Calendar.getInstance().getTime();
	}

	public static java.sql.Date getCurrentSqlDate() {
		return new java.sql.Date(getCurrentUtilDate().getTime());
	}

	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(getCurrentUtilDate().getTime());
	}

	public static String getCurrentDateTime(String pattern) {
		return toString(getCurrentUtilDate(), pattern);
	}

	public static Timestamp utilDateToTimestamp(java.util.Date date) {
		if (date != null) {
			return new Timestamp(date.getTime());
		}
		return null;
	}

	public static java.util.Date timestampToUtilDate(Timestamp timestamp) {
		if (timestamp != null) {
			return new java.util.Date(timestamp.getTime());
		}
		return null;
	}

	public static java.sql.Date utilDateToSqlDate(java.util.Date date) {
		if (date != null) {
			return new java.sql.Date(date.getTime());
		}
		return null;
	}

	public static java.util.Date sqlDateToUtilDate(java.sql.Date date) {
		if (date != null) {
			return new java.util.Date(date.getTime());
		}
		return null;
	}

	public static boolean isDateFormat(String strDate, String pattern) {
		return (strToUtilDate(strDate, pattern) != null);
	}

	public static java.util.Date strToUtilDate(String strDate, String pattern) {
		if ((strDate != null) && (strDate.trim().length() > 0)) {
			SimpleDateFormat smf = new SimpleDateFormat(pattern);
			ParsePosition pos = new ParsePosition(0);
			smf.setLenient(false);
			return smf.parse(strDate, pos);
		}
		return null;
	}

	public static java.sql.Date strToSqlDate(String strDate, String pattern) {
		return utilDateToSqlDate(strToUtilDate(strDate, pattern));
	}

	public static Timestamp strToTimestamp(String strDate, String pattern) {
		java.util.Date date = strToUtilDate(strDate, pattern);
		return utilDateToTimestamp(date);
	}

	public static boolean isWeekEnd(java.util.Date strDate) {
		boolean bRtn = false;
		int day_of_week = 1;
		Calendar cal = Calendar.getInstance();
		if (strDate == null)
			return false;
		cal.setTime(strDate);
		day_of_week = cal.get(7);
		if ((day_of_week == 1) || (day_of_week == 7)) {
			bRtn = true;
		}
		return bRtn;
	}

	public static int getLastDayOfMonth(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int month = cal.get(2);
		boolean isLeapYear = ((GregorianCalendar) cal).isLeapYear(cal.get(1));
		int lastDay;
		if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10)
				|| (month == 12)) {
			lastDay = 31;
		} else if ((month == 4) || (month == 6) || (month == 9) || (month == 11))
			lastDay = 31;
		else if ((month == 2) && (isLeapYear))
			lastDay = 29;
		else {
			lastDay = 28;
		}
		return lastDay;
	}

	public static final java.util.Date getDateAfter(java.util.Date date, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}

	public static final java.sql.Date getDateAfter(java.sql.Date date, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		return utilDateToSqlDate(cal.getTime());
	}

	public static final java.util.Date getDateBefore(java.util.Date date, int field, int amount) {
		return getDateAfter(date, field, -amount);
	}

	public static final java.util.Date getNextDay(java.util.Date date) {
		return getDateAfter(date, 6, 1);
	}

	public static final java.util.Date getPreviousDay(java.util.Date date) {
		return getDateBefore(date, 6, 1);
	}
	

	/**
	 * �����ĸ�ʽ����ʽ����Ҫ�����date ��ʽ�� pattern ���date ���� null���򷵻ص�ǰ��date�ַ�
	 * 
	 * @param date
	 *            java.util.Date
	 * @return short format datetime
	 */
	public static String dateFormatterByPattern(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if (date != null) {
			return sdf.format(date);
		} else {
			return sdf.format(new Date());
		}
	}

	public static final java.util.Date getDate(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(9, 0);
		cal.set(10, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);
		return cal.getTime();
	}

	public static final Timestamp getDate(Timestamp origTs) {
		Timestamp newTs = null;
		Calendar c = Calendar.getInstance();
		c.setTime(origTs);
		c.set(13, 0);
		c.set(11, 0);
		c.set(12, 0);
		c.set(14, 0);
		newTs = new Timestamp(c.getTime().getTime());
		return newTs;
	}

	public static java.sql.Date getDate(java.sql.Date origDate) {
		java.sql.Date newDate = null;
		Calendar c = Calendar.getInstance();
		c.setTime(origDate);
		c.set(13, 0);
		c.set(11, 0);
		c.set(12, 0);
		c.set(14, 0);
		newDate = new java.sql.Date(c.getTime().getTime());
		return newDate;
	}

	public static String toString(Timestamp timestamp, String pattern) {
		if ((pattern == null) || (pattern.equals("")))
			pattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String dateString = null;
		if (timestamp != null) {
			dateString = sdf.format(timestamp);
		}
		return dateString;
	}

	public static String toString(java.sql.Date date, String pattern) {
		if ((pattern == null) || (pattern.equals("")))
			pattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String dateString = null;
		if (date != null) {
			dateString = sdf.format(date);
		}
		return dateString;
	}

	public static String toString(java.util.Date date, String pattern) {
		if ((pattern == null) || (pattern.equals("")))
			pattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String dateString = null;
		if (date != null) {
			dateString = sdf.format(date);
		}
		return dateString;
	}

	public static java.sql.Date getInvalidDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(1, 1896);
		cal.set(2, 0);
		cal.set(5, 1);
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);

		return utilDateToSqlDate(cal.getTime());
	}

	public static String getDatePattern(String language) {
		String pattern = null;

		if (language.equalsIgnoreCase("en-US")) {
			pattern = "dd/MM/yyyy";
		} else if (language.equalsIgnoreCase("zh-CN")) {
			pattern = "yyyy-MM-dd";
		} else if (language.equalsIgnoreCase("zh-HK")) {
			pattern = "dd/MM/yyyy";
		} else
			pattern = "dd/MM/yyyy";

		return pattern;
	}

	public static String getDatetimePattern(String language) {
		String pattern = null;
		if (language.equalsIgnoreCase("en-US")) {
			pattern = "dd/MM/yyyy HH:mm:ss";
		} else if (language.equalsIgnoreCase("zh-CN")) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		} else if (language.equalsIgnoreCase("zh-HK")) {
			pattern = "dd/MM/yyyy HH:mm:ss";
		} else
			pattern = "dd/MM/yyyy HH:mm:ss";

		return pattern;
	}

	public static long sqlDateToJD(java.sql.Date date) {
		if (date == null) {
			return 0L;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(1);
		int month = cal.get(2) + 1;
		int day = cal.get(5);
		int ggg = 1;
		int s = 1;

		if (year <= 1585)
			ggg = 0;
		double result = -1.0D * Math.floor(7.0D * (Math.floor((month + 9) / 12) + year) / 4.0D);

		s = 1;
		if (month - 9 < 0)
			s = -1;
		int a = Math.abs(month - 9);
		double j1 = Math.floor(year + s * Math.floor(a / 7));
		j1 = -1.0D * Math.floor((Math.floor(j1 / 100.0D) + 1.0D) * 3.0D / 4.0D);

		result = result + Math.floor(275 * month / 9) + day + ggg * j1;
		result = result + 1721027.0D + 2 * ggg + 367 * year;

		return Math.round(result);
	}

	public static boolean before(java.sql.Date st, java.sql.Date ed) {
		if ((null != st) && (null != ed)) {
			return st.before(ed);
		}
		return false;
	}

	public static boolean before(java.util.Date st, java.util.Date ed) {
		if ((null != st) && (null != ed)) {
			return st.before(ed);
		}
		return false;
	}

	public static boolean before(String st, String ed, String pattern) {
		if ((null != st) && (null != ed)) {
			return before(strToUtilDate(st, pattern), strToUtilDate(ed, pattern));
		}

		return false;
	}

	public static boolean after(java.sql.Date st, java.sql.Date ed) {
		if ((null != st) && (null != ed)) {
			return st.after(ed);
		}
		return false;
	}

	public static boolean isExpired(String date, String pattern) {
		SimpleDateFormat s = new SimpleDateFormat(pattern);
		String c = s.format(Calendar.getInstance().getTime());
		if (c != null) {
			ParsePosition pos = new ParsePosition(0);
			java.util.Date d = s.parse(c, pos);
			ParsePosition pos2 = new ParsePosition(0);
			java.util.Date d2 = s.parse(date, pos2);
			if (d.compareTo(d2) > 0) {
				return true;
			}
		}
		return false;
	}

	public static boolean isExpired(java.util.Date date, String pattern) {
		SimpleDateFormat s = new SimpleDateFormat(pattern);
		String c = s.format(Calendar.getInstance().getTime());
		if (c != null) {
			ParsePosition pos = new ParsePosition(0);
			java.util.Date d = s.parse(c, pos);
			if (d.compareTo(date) > 0) {
				return true;
			}
		}
		return false;
	}

	public static boolean dateContain(java.sql.Date st1, java.sql.Date ed1, java.sql.Date st2, java.sql.Date ed2) {
		if ((null == st1) || (null == ed1) || (null == st2) || (null == ed2))
			return false;
		if ((st1.equals(st2)) && (ed1.equals(ed2)))
			return true;
		if ((st1.equals(st2)) && (ed1.after(ed2)))
			return true;
		if ((st1.before(st2)) && (ed1.equals(ed2))) {
			return true;
		}
		return ((st1.before(st2)) && (ed1.after(ed2)));
	}

	public static int compareCurrentDay(java.sql.Date effFrDate, java.sql.Date effToDate) {
		int result = 0;
		if (before(effFrDate, effToDate)) {
			if ((before(effFrDate, getCurrentSqlDate())) && (before(effToDate, getCurrentSqlDate()))) {
				result = 1;
			} else if ((before(effFrDate, getCurrentSqlDate())) && (after(effToDate, getCurrentSqlDate()))) {
				result = 2;
			} else if ((after(effFrDate, getCurrentSqlDate())) && (after(effToDate, getCurrentSqlDate()))) {
				result = 3;
			}
		}
		return result;
	}

	public static java.util.Date getFirstDayByYear(int year) {
		java.util.Date rtnDate = null;
		Calendar cal = Calendar.getInstance();
		cal.set(1, year);
		cal.set(2, 0);
		cal.set(5, 1);
		rtnDate = cal.getTime();
		return rtnDate;
	}

	public static int getJulianateByDate(java.sql.Date date) {
		return getJulianateByDate(sqlDateToUtilDate(date));
	}

	public static int getJulianateByDate(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayOfYear = cal.get(6);
		return dayOfYear;
	}

	public static int getDayOfWeek(java.sql.Date date, Locale locale) {
		Calendar cal = Calendar.getInstance(locale);
		cal.setTime(date);
		int dayOfWeek = cal.get(7);
		return dayOfWeek;
	}
	
	public static String getDate(int period) {
	   Calendar calendar = Calendar.getInstance();
       if (period == 1) {
           calendar.add(Calendar.DATE, 0);
       }
       if (period == 2) {
           calendar.add(Calendar.DATE, -2);
       }
       if (period == 3) {
           int dday = calendar.get(Calendar.DAY_OF_WEEK) - 1;
           calendar.add(Calendar.DATE, (dday * -1));
       }
       if (period == 4) {
           int dday = calendar.get(Calendar.DAY_OF_MONTH);
           calendar.add(Calendar.DATE, (dday * -1));
       }
       if (period == 5) {
           calendar.add(Calendar.MONTH, -5);
           // calendar.set(Calendar.YEAR, Calendar.MONTH, 1);
       }
       if (period == 6) {
    	   calendar.add(Calendar.DATE, -6);
       }
       return DATE_FORMAT.format(calendar.getTime());
   }
	
	/**
	 * ȡ��������ֵ
	 * @param period
	 * @return
	 */
	public static String getBeforeDate(int period) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, period);
		return DATE_FORMAT.format(calendar.getTime());
	}
	
	public static final Date addDay(Date date, int days) {
		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		cale.add(Calendar.DAY_OF_YEAR, days);
		return cale.getTime();
	}

	public static String getCurrenDate() {
		String date = DATE_FORMAT.format(new Date());
		return date;
	}

	public static long getDaysAgoMs(int days){
		try {
			Date date = DATE_FORMAT.parse(getCurrenDate());
			date = DateUtil.addDay(date, -days);
			return date.getTime()/100000;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static long getDaysAgoMs(String dstr){
		try {
			Date date = DATE_FORMAT.parse(dstr);
			return date.getTime()/100000;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * ��������ַ�ȡ������long
	 * @param dstr
	 * @return
	 */
	public static long getLongByDstr(String dstr,String format){
		try {
			Date date = new SimpleDateFormat(format).parse(dstr);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
	public static Timestamp getLongByDstr2(String dstr){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setLenient(false);
		try {
			Timestamp ts = new Timestamp(format.parse(dstr).getTime());
			return ts;
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String formatTime(long time,String format){
		String date = new SimpleDateFormat(format).format(new Date(time));
		return date;
	}
	
	/**
	 * ���ظ�ʽ ���� 24Сʱ�� �� ���� 24Сʱ�� 
	 * @param date
	 * @return
	 */
	public static String formatTimeString(Date date){
		Calendar today=Calendar.getInstance();
		today.set(Calendar.HOUR, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		
		if(date.getTime()>today.getTimeInMillis()){
			//����
			return "���� "+toString(date, "HH:mm");
		}else{
			return toString(date,"MM��dd�� HH:mm");
		}
	}
	
	/**
	 * ��ȡ�������
	 * @param days �Խ���Ϊ׼����ǰ���������������ǰΪ�������Ϊ����
	 * @return
	 */
	public static Timestamp getDayBegin(int days){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		return new Timestamp(cal.getTimeInMillis());
	}
	
	public static Date getDate(int hour, int minute, int second, int milliSecond) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.HOUR_OF_DAY, hour);

		cal.set(Calendar.SECOND, second);

		cal.set(Calendar.MINUTE, minute);

		cal.set(Calendar.MILLISECOND, milliSecond);

		Date date = new Date(cal.getTimeInMillis());

		try {

			return format.parse(format.format(date));

		} catch (ParseException e) {

			e.printStackTrace();

		}

		return null;

	}
	
	public static Date getDate(int day,int hour, int minute, int second, int milliSecond) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.DAY_OF_MONTH, day);
		
		cal.set(Calendar.HOUR_OF_DAY, hour);

		cal.set(Calendar.SECOND, second);

		cal.set(Calendar.MINUTE, minute);

		cal.set(Calendar.MILLISECOND, milliSecond);

		Date date = new Date(cal.getTimeInMillis());

		try {

			return format.parse(format.format(date));

		} catch (ParseException e) {

			e.printStackTrace();

		}

		return null;

	}
	public static Date getDate(int hour, int minute) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.HOUR_OF_DAY, hour);

		cal.set(Calendar.MINUTE, minute);
		
		Date date = new Date(cal.getTimeInMillis());
		
		try {

			return format.parse(format.format(date));

		} catch (ParseException e) {

			e.printStackTrace();

		}

		return null;

	}
	public static long getDateTime(int hour, int minute) {

		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.HOUR_OF_DAY, hour);

		cal.set(Calendar.MINUTE, minute);

		return cal.getTimeInMillis();

	}
	public static long getDateTime( int minute) {

		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.MINUTE, minute);

		return cal.getTimeInMillis();

	}
	public static String getNextDayStr(String datestr){
		try {
			Date date = DATE_FORMAT.parse(datestr);
			date = getNextDay(date);
			datestr = dateFormatterByPattern(date, "yyyy-MM-dd");
			return datestr;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "0";
		}
		
	}
	
	/**
	 * ���� ���� ����1
	 * @return
	 */
	public static Date getMonday() {
		Calendar calendar = Calendar.getInstance();
		int index = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.add(Calendar.DATE, 2-index);
		return calendar.getTime();
	}
	/**
	 * ���� ���� ������
	 * @return
	 */
	public static Date getSunday() {
		Calendar calendar = Calendar.getInstance();
		int index = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.add(Calendar.DATE, 8-index);
		return calendar.getTime();
	}
	/**
	 * ��ȡ���������ڼ�
	 * 
	 *	 SUN 	MON 	TUE 	WED 	THU 	FRI 	SAT
	 *	  1 	 2 	     3 	     4 	     5 	     6 	     7
	 * @param date
	 * @return
	 */
	public static int getWeekOfDate(Date date) {      
	    Calendar calendar = Calendar.getInstance();      
	    if(date != null){        
	         calendar.setTime(date);      
	    }        
	    return (calendar.get(Calendar.DAY_OF_WEEK));      
	}
	
    /**
     * ȡ�³�����
     * @param date
     * @return
     */
    public static Date getMonthStart() {
        Calendar calendar = Calendar.getInstance();
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, (1 - index));
        return calendar.getTime();
    }
    
    /**
     * ȡ��ĩ����
     * @param date
     * @return
     */
    public static Date getMonthEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, (-index));
        return calendar.getTime();
    }
    
	public static Date getBeforeWeek(Date date,int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, i);
		return calendar.getTime();
	}
	
	public static Date getBeforeMonth(Date date,int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, i);
		return calendar.getTime();
	}
	
	public static Date getSunday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int index = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.add(Calendar.DATE, 8-index);
		return calendar.getTime();
	}
	
    /**
     * ȡ�³�����
     * @param date
     * @return
     */
    public static Date getLastMonth(int last) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, last);
        return calendar.getTime();
    }
    
    /**
     * ȡ�³�����
     * @param date
     * @return
     */
    public static Date getMonthStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, (1 - index));
        return calendar.getTime();
    }
    
    /**
     * ȡ��ĩ����
     * @param date
     * @return
     */
    public static Date getMonthEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, (-index));
        return calendar.getTime();
    }
}
