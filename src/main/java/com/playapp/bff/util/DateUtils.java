package com.playapp.bff.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtils {

	public static String getDaysForAccuWeatherDetails(String date) throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateFormatted = LocalDate.parse(date, formatter);
		LocalDate today = LocalDate.now();
		LocalDate fiveDaysLater = today.plusDays(5);

		if (today.toString().equals(dateFormatted.toString())) {
			return "1";
		} else {
			if (!dateFormatted.isBefore(today) && !dateFormatted.isAfter(fiveDaysLater)) {
				return "5";
			} else {
				throw new Exception("Fecha no válida");
			}
		}
	}

	public static int countDaysFromToday(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateFormatted = LocalDate.parse(date, formatter);
		LocalDate today = LocalDate.now();

		int daysBetween = (int) ChronoUnit.DAYS.between(today, dateFormatted);
		return daysBetween;
	}

}
