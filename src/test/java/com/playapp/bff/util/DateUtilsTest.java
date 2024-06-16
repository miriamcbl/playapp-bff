package com.playapp.bff.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import com.playapp.bff.constants.Constants;
import com.playapp.bff.constants.ErrorConstants;

class DateUtilsTest {

	@Test
	void getDaysForAccuWeatherPredictionTodayTest() {
		LocalDate today = LocalDate.now();
		String todayStr = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		assertEquals(Constants.ONE_DAY_PREDICTION, DateUtils.getDaysForAccuWeatherPrediction(todayStr));
	}

	@Test
	void getDaysForAccuWeatherPredictionWithinFiveDaysTest() {
		LocalDate today = LocalDate.now();
		LocalDate withinFiveDays = today.plusDays(3);
		String withinFiveDaysStr = withinFiveDays.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		assertEquals(Constants.FIVE_DAYS_PREDICTION, DateUtils.getDaysForAccuWeatherPrediction(withinFiveDaysStr));
	}

	@Test
	void getDaysForAccuWeatherPredictionBeyondFiveDaysTest() {
		LocalDate today = LocalDate.now();
		LocalDate beyondFiveDays = today.plusDays(6);
		String beyondFiveDaysStr = beyondFiveDays.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			DateUtils.getDaysForAccuWeatherPrediction(beyondFiveDaysStr);
		});
		assertEquals(ErrorConstants.DATE_ERROR, exception.getMessage());
	}

	@Test
	void getDaysForAccuWeatherPredictionPastDateTest() {
		LocalDate today = LocalDate.now();
		LocalDate pastDate = today.minusDays(1);
		String pastDateStr = pastDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			DateUtils.getDaysForAccuWeatherPrediction(pastDateStr);
		});
		assertEquals(ErrorConstants.DATE_ERROR, exception.getMessage());
	}

	@Test
	void countDaysFromTodayValidTest() {
		LocalDate today = LocalDate.now();
		LocalDate futureDate = today.plusDays(3);
		String futureDateStr = futureDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		assertEquals(3, DateUtils.countDaysFromToday(futureDateStr));
	}

	@Test
	void countDaysFromTodayPastDateTest() {
		LocalDate today = LocalDate.now();
		LocalDate pastDate = today.minusDays(1);
		String pastDateStr = pastDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			DateUtils.countDaysFromToday(pastDateStr);
		});
		assertEquals(ErrorConstants.DATE_ERROR, exception.getMessage());
	}

	@Test
	void countDaysFromTodayBeyondFiveDaysTest() {
		LocalDate today = LocalDate.now();
		LocalDate beyondFiveDays = today.plusDays(6);
		String beyondFiveDaysStr = beyondFiveDays.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			DateUtils.countDaysFromToday(beyondFiveDaysStr);
		});
		assertEquals(ErrorConstants.DAYS_ERROR, exception.getMessage());
	}

}
