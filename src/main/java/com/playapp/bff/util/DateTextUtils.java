package com.playapp.bff.util;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.playapp.bff.constants.CadizTownsGeographicCoordinates;
import com.playapp.bff.constants.ErrorConstants;
import com.playapp.bff.constants.PromptConstants;

public class DateTextUtils {

	private DateTextUtils() {
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
			return PromptConstants.ONE_DAY_PREDICTION;
		} else {
			if (!dateFormatted.isBefore(today) && !dateFormatted.isAfter(fiveDaysLater)) {
				return PromptConstants.FIVE_DAYS_PREDICTION;
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

	/**
	 * Checks if is this year.
	 *
	 * @param date the date
	 * @return true, if is this year
	 */
	public static boolean isNotThisYear(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateFormatted = LocalDate.parse(date, formatter);
		int actualYear = LocalDate.now().getYear();
		int yearDateFormatted = dateFormatted.getYear();
		return actualYear != yearDateFormatted;
	}

	/**
	 * Date has year.
	 *
	 * @param date the date
	 * @return true, if successful
	 */
	public static boolean dateHasYear(String date) {
		return date.length() > 6 && date.length() <= 10;
	}

	/**
	 * Adds the actual year.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static String addActualYear(String date) {
		return date + "/" + String.valueOf(LocalDate.now().getYear());
	}

	/**
	 * Gets the formatted beaches.
	 *
	 * @return the formatted beaches
	 */
	public static List<String> getFormattedBeaches() {
		List<String> names = new ArrayList<>();
		Arrays.asList(CadizTownsGeographicCoordinates.values()).stream().forEach(beach -> {
			String name = beach.name().replaceAll("_", " ");
			names.add(name);
		});
		return names;
	}

	/**
	 * Normalize text.
	 *
	 * @param text the text
	 * @return the string
	 */
	public static String normalizeText(String text) {
		return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[\u0300-\u0301]", "");
	}

}
