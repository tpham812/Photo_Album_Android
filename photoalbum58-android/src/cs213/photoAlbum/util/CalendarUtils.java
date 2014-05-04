package cs213.photoAlbum.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


// TODO: Auto-generated Javadoc
/**
 * Utility class to process Calendar.
 * @author dheeptha
 */
public class CalendarUtils {

	/** The Constant DATE_FMT. */
	private static final String DATE_FMT = "MM/dd/yyyy-HH:mm:ss";

	/**
	 * Formats the calendar.
	 *
	 * @param time the time
	 * @return the calendar
	 */
	public static Calendar toCalendar(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}
	
	/**
	 * Parses the date.
	 *
	 * @param string the string
	 * @return the calendar
	 * @throws ParseException the parse exception
	 */
	public static Calendar parseDate(String string) throws ParseException {

		Calendar cal = Calendar.getInstance();
		Date dateStr = null;
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FMT);
		dateStr = formatter.parse(string);
		cal.setTime(dateStr);
		cal.set(Calendar.MILLISECOND, 0);

		return cal;
	}
	
	/**
	 * Formats the date.
	 *
	 * @param cal the calendar
	 * @return the string
	 */
	public static String toFmtDate(Calendar cal) {
		
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FMT);
		return formatter.format(cal.getTime());
	}
}
