package com.rii.wp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

	/**
	 * Method defines the conversion of date to string.
	 * 
	 * 
	 * @param date
	 * @return- string value of the date,in the given format.
	 */
	public static String convertDateToString(final Date date, final String format) {
		SimpleDateFormat s = new SimpleDateFormat(format);
		return s.format(date);
	}

	public static String convertDateToString(final java.sql.Date date, final String format) {
		SimpleDateFormat s = new SimpleDateFormat(format);
		return s.format(date);
	}

	public static Date convertStringToDate(final String dateString, final String format)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(dateString);
	}

	public static String convertDateStringToString(final String dateString, final String fromFormat, final String toFormat)
			throws ParseException {

		SimpleDateFormat sdfFrom = new SimpleDateFormat(fromFormat);
		SimpleDateFormat sdfTo = new SimpleDateFormat(toFormat);
		return sdfTo.format(sdfFrom.parse(dateString));
	}

	public static Date convertToDate(final int year, final int month, final int date)
			throws ParseException {
		Calendar c = Calendar.getInstance(TimeZone.getDefault());
		c.set(year, month, date);
		return c.getTime();
	}
	
	public static int getDayOfyear(final Date date){
		Calendar c = Calendar.getInstance(TimeZone.getDefault());
		c.setTime(date);
		return c.get(Calendar.DAY_OF_YEAR);
	}
	
	public static int getDayOfyear(final String dateString) throws ParseException{
		Calendar c = Calendar.getInstance(TimeZone.getDefault());
		c.setTime(convertStringToDate(dateString, "dd/MM/yy"));
		return c.get(Calendar.DAY_OF_YEAR);
	}
	
	public static Date getCurrentDateWithLocalTimeZone(){
		Calendar c = Calendar.getInstance(TimeZone.getDefault());
		return c.getTime();
		
	}

	public static Date getNthDateWithLocalTimeZone(final int i) {
		Calendar c = Calendar.getInstance(TimeZone.getDefault());
		c.add(Calendar.DAY_OF_MONTH, -i);
		return c.getTime();
	}
	
}
