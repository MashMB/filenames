package net.bedra.maciej.filenames.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utils for time calculations.
 *
 * @author Maciej Bedra
 */
public class TimeUtils {

	/**
	 * Convert milliseconds to seconds.
	 *
	 * @param milliseconds time in milliseconds
	 * @return double time in seconds
	 */
	public static double convertToSeconds(long milliseconds) {
		return roundToTwoDecimal(milliseconds / 1000);
	}

	/**
	 * Round number to two decimal places.
	 *
	 * @param value value to convert
	 * @return double converted value with two decimal places
	 */
	private static double roundToTwoDecimal(double value) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, RoundingMode.HALF_UP);

		return bd.doubleValue();
	}

}
