package com.playapp.bff.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.playapp.bff.constants.Constants;
import com.playapp.bff.constants.ErrorConstants;

public class DateUtils {

	private DateUtils() {
		super();
	}

	/**
	 * Gets the days for accu weather prediction.
	 *
	 * @param date the date
	 * @return the days for accu weather prediction
	 */
	public static String getDaysForAccuWeatherPrediction(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateFormatted = LocalDate.parse(date, formatter);
		LocalDate today = LocalDate.now();
		LocalDate fiveDaysLater = today.plusDays(5);

		if (today.toString().equals(dateFormatted.toString())) {
			return Constants.ONE_DAY_PREDICTION;
		} else {
			if (!dateFormatted.isBefore(today) && !dateFormatted.isAfter(fiveDaysLater)) {
				return Constants.FIVE_DAYS_PREDICTION;
			} else {
				throw new IllegalArgumentException(
						ErrorConstants.DATE_ERROR);
			}
		}
	}

	/**
	 * Count days from today.
	 *
	 * @param date the date
	 * @return the int
	 */
	public static int countDaysFromToday(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateFormatted = LocalDate.parse(date, formatter);
		LocalDate today = LocalDate.now();
		if (dateFormatted.isBefore(today)) {
			throw new IllegalArgumentException(ErrorConstants.DATE_ERROR);
		}
		int daysBetween = (int) ChronoUnit.DAYS.between(today, dateFormatted);
		if (daysBetween > 5) {
			throw new IllegalArgumentException(ErrorConstants.DAYS_ERROR);
		}
		return daysBetween;
	}

}
