/**
 * 
 */
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author RAMESHKUMAR
 * 
 */
public class Utils implements java.io.Serializable {

	private static final long serialVersionUID = 8844503617068293995L;
	
	/**
	 * Returns boolean if String value passed in is null/blank
	 * 
	 * @param value
	 * @return boolean false, if parameter value is null or empty
	 * @return boolean true, if parameter is NOT null or empty
	 */
	public static boolean isNullOrEmpty(String value) {
		return (value == null || value.trim().length() == 0);
	}

	/**
	 * @param obj
	 *            obj
	 * @return : boolean
	 * 
	 */
	public static boolean isNumeric(String obj) {
		if ((obj == null) || (obj.length() == 0)) {
			return false;
		}
		return obj.matches("[0-9]+");
	}

	/**
	 * isAlphaNumeric.
	 * 
	 * @param obj
	 *            obj
	 * @return true if string is all alpha numeric
	 */
	public static boolean isAlphaNumeric(String obj) {
		if ((obj == null) || (obj.length() == 0)) {
			return false;
		}
		return obj.matches("[a-zA-Z0-9]+");
	}

	/**
	 * @param obj
	 *            obj
	 * @return : boolean
	 * 
	 */
	public static boolean isDate(String obj) {
		SimpleDateFormat df = new SimpleDateFormat(ServiceConstants.DATE_FORMAT);
		try {
			df.parse(obj);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	

	/**
	 * @param obj
	 *            obj
	 * @return : Date
	 * 
	 */
	public static Date toUtilDate(String obj) {
		Date utilDate = null;
		SimpleDateFormat df = new SimpleDateFormat(ServiceConstants.DATE_FORMAT);
		try {
			utilDate = df.parse(obj);
		} catch (ParseException e) {
		}
		return utilDate;
	}
	
	/**
	 * @param obj
	 *            obj
	 * @return : Date
	 * 
	 */
	public static Date toSqlDate(String obj) {
		Date utilDate = null;
		SimpleDateFormat df = new SimpleDateFormat(ServiceConstants.DATE_FORMAT);
		try {
			utilDate = df.parse(obj);
		} catch (ParseException e) {
		}
		return new java.sql.Date(utilDate.getTime());
	}
	
	/**
	 * Get elapsed time since start time
	 * 
	 * @param startTime
	 *            - start time in milliseconds
	 * @return
	 */
	public static long getElapsedTime(long startTime) {
		try {
			long elapsedTime = System.currentTimeMillis() - startTime;
			return elapsedTime;
		} catch (Exception ex) {
			return 0;
		}
	}

	/**
	 * Date conversion function
	 * 
	 * @param date
	 *            input data
	 * @return date in string format
	 */
	public static String toMMDDYYYY(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(
				ServiceConstants.DATE_FORMAT);
		if (null != date) {
			return sdf.format(date);
		}
		return null;
	}
	
	/**
	 * Is the given date is not lesser than Today
	 * 
	 * @param date
	 *            input data
	 * @return 'true', if the given data is greater than or equal Today. 'false'
	 *         otherwise.
	 */
	public static boolean isGreaterThanToday(Date date) {
		
		DateFormat df = new SimpleDateFormat(ServiceConstants.DB_DATE_FORMAT);
		Date todayDate = null;
		try {
			todayDate = df.parse(df.format(new Date() ));
		} catch (ParseException e) {
			System.out.println("Invalid date '" + date.toString() + "'");
		}
		
		int retValue = date.compareTo(todayDate);
		
		if (retValue != -1) {
			return true;
		}
		return false;
	}	


	/**
	 * Converts weight from kg's to lbs
	 * 
	 * @param the
	 *            weight in kg's
	 * @return lbs
	 */
	public static int convertKgsToLbs(double d) {
		int lbs = 0;
		if (String.valueOf(d) != null && String.valueOf(d).trim().length() != 0) {
			lbs = (int) Math.floor(d * 2.2);
		}
		return lbs;
	}

	/**
	 * Converts height from cm's to feet with inches
	 * 
	 * @param the
	 *            height in cm's
	 * @return feet with inches
	 */
	public static String convertCmsToFeet(double d) {
		int feetPart = 0;
		int inchesPart = 0;
		if (String.valueOf(d) != null && String.valueOf(d).trim().length() != 0) {
			feetPart = (int) Math.floor((d / 2.54) / 12);
			inchesPart = (int) Math.ceil((d / 2.54) - (feetPart * 12));
		}
		return String.format("%d ft %d in", feetPart, inchesPart);
	}
	
	public static String getMonth(Date current, int minus) throws ParseException {
		Calendar cal = Calendar.getInstance();

		cal.setTime(current);
		cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH) + minus));
		current = cal.getTime();

		return getMonth(current);
	}

	/**
	 * Returns month name from date
	 * 
	 * @param date
	 *            format
	 * @return month name
	 */
	public static String getMonth(Date date) throws ParseException {
		DateFormat sdf = new SimpleDateFormat(ServiceConstants.DB_DATE_FORMAT);
		String dateStr = sdf.format(date);
		DateFormat format2 = new SimpleDateFormat(ServiceConstants.MONTH_FORMAT);
		Date dateObj = sdf.parse(dateStr);
		return format2.format(dateObj);
	}
	
	public static float truncateDecimal(float x) {
		return truncateDecimal(x, ServiceConstants.DEFAULT_NUMBER_OF_DECIMALS);
	}
	
	public static float truncateDecimal(float x, int numberofDecimals) {
		if (x > 0) {
			return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals,
					BigDecimal.ROUND_FLOOR).floatValue();
		} else {
			return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals,
					BigDecimal.ROUND_CEILING).floatValue();
		}
	}
	
	public static String getCurrentTimeAsString() {
		java.text.DateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyyMMddHHmmssSSSS");
		java.util.Date date = new java.util.Date();
		// convert to GMT, if needed, look at this
		return dateFormat.format(date);
	}
	
	public static Date getCorrectedDate(int val) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, val);
		return cal.getTime();
	}
	
	public static float getRandomNumber(float low, float high) {
		Random r = new Random();
		float Low = (low - (low*0.1f));
		float High = (high + (high * 0.1f));
		float Result = r.nextFloat() * (High - Low) + Low;
		return (float)(Math.round(Result * 100.0) / 100.0);
	}
	
	public static int getAge(Date dateOfBirth) {
		Calendar today = Calendar.getInstance();
		Calendar birthDate = Calendar.getInstance();

		int age = 0;

		birthDate.setTime(dateOfBirth);
		if (birthDate.after(today)) {
			throw new IllegalArgumentException("Can't be born in the future");
		}
		age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

		// If birth date is greater than todays date 
		// (after 2 days adjustment of leap year) then decrement age one year
		if ((birthDate.get(Calendar.DAY_OF_YEAR)
				- today.get(Calendar.DAY_OF_YEAR) > 3)
				|| (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH))) {
			age--;
			
		// If birth date and todays date are of same month and birth day of month 
		// is greater than todays day of month then decrement age
		} else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH))
				&& (birthDate.get(Calendar.DAY_OF_MONTH) > today
						.get(Calendar.DAY_OF_MONTH))) {
			age--;
		}

		return age;
	}
	
	public static void main(String[] areg) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		
		try {
			System.out.println(Utils.getAge(sdf.parse("07-07-1924")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
