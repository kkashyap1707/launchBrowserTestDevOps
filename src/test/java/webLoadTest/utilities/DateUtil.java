package webLoadTest.utilities;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DateUtil {

	public static String futureDateReturn(int numberOfDays,String pattern) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		java.util.Date newDate = org.apache.commons.lang3.time.DateUtils.addDays(new java.util.Date(), numberOfDays);
		return simpleDateFormat.format(newDate);
	}

	public static String futureHoursReturn(int numberOfHours,String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		java.util.Date newDate = org.apache.commons.lang3.time.DateUtils.addHours(new java.util.Date(), numberOfHours);
		return simpleDateFormat.format(newDate);
	}

	public static String futureMonthReturn(int numberOfMonths,String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		java.util.Date newDate = org.apache.commons.lang3.time.DateUtils.addMonths(new java.util.Date(), numberOfMonths);
		return simpleDateFormat.format(newDate);
	}

	public static String futureMonthTimeGMT(int numberOfMonths,String pattern, String Timezone) {
		java.util.Date newDate = org.apache.commons.lang3.time.DateUtils.addMonths(new java.util.Date(), numberOfMonths);
		SimpleDateFormat converter = new SimpleDateFormat(pattern);
		converter.setTimeZone(TimeZone.getTimeZone(Timezone));
		return converter.format(newDate);
	}

	//DateUtil.currentDateTime("MM/dd/yyyy hh:mm:ss a",InputGenerator.faker.address().timeZone())
	public static String currentDateTime(String pattern, String Timezone) {
		java.util.Date localTime = new java.util.Date();
		SimpleDateFormat converter = new SimpleDateFormat(pattern);
		converter.setTimeZone(TimeZone.getTimeZone(Timezone));
		String currentDate = converter.format(localTime);
		return currentDate;
	}

	public static String currentYear(String pattern, String Timezone) {
		java.util.Date localTime = new java.util.Date();
		SimpleDateFormat converter = new SimpleDateFormat(pattern);
		converter.setTimeZone(TimeZone.getTimeZone(Timezone));
		String currentDate = converter.format(localTime);
		return currentDate;
	}
}