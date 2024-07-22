package com.playapp.bff.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import com.playapp.bff.constants.PromptConstants;
import com.playapp.bff.constants.ErrorConstants;

class DateUtilsTest {

	@Test
	void getDaysForAccuWeatherPredictionTodayTest() {
		LocalDate today = LocalDate.now();
		String todayStr = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		assertEquals(PromptConstants.ONE_DAY_PREDICTION, DateTextUtils.getDaysForAccuWeatherPrediction(todayStr));
	}

	@Test
	void getDaysForAccuWeatherPredictionWithinFiveDaysTest() {
		LocalDate today = LocalDate.now();
		LocalDate withinFiveDays = today.plusDays(3);
		String withinFiveDaysStr = withinFiveDays.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		assertEquals(PromptConstants.FIVE_DAYS_PREDICTION, DateTextUtils.getDaysForAccuWeatherPrediction(withinFiveDaysStr));
	}

	@Test
	void getDaysForAccuWeatherPredictionBeyondFiveDaysTest() {
		LocalDate today = LocalDate.now();
		LocalDate beyondFiveDays = today.plusDays(6);
		String beyondFiveDaysStr = beyondFiveDays.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			DateTextUtils.getDaysForAccuWeatherPrediction(beyondFiveDaysStr);
		});
		assertEquals(ErrorConstants.DATE_ERROR, exception.getMessage());
	}

	@Test
	void getDaysForAccuWeatherPredictionPastDateTest() {
		LocalDate today = LocalDate.now();
		LocalDate pastDate = today.minusDays(1);
		String pastDateStr = pastDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			DateTextUtils.getDaysForAccuWeatherPrediction(pastDateStr);
		});
		assertEquals(ErrorConstants.DATE_ERROR, exception.getMessage());
	}

	@Test
	void countDaysFromTodayValidTest() {
		LocalDate today = LocalDate.now();
		LocalDate futureDate = today.plusDays(3);
		String futureDateStr = futureDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		assertEquals(3, DateTextUtils.countDaysFromToday(futureDateStr));
	}

	@Test
	void countDaysFromTodayPastDateTest() {
		LocalDate today = LocalDate.now();
		LocalDate pastDate = today.minusDays(1);
		String pastDateStr = pastDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			DateTextUtils.countDaysFromToday(pastDateStr);
		});
		assertEquals(ErrorConstants.DATE_ERROR, exception.getMessage());
	}

	@Test
	void countDaysFromTodayBeyondFiveDaysTest() {
		LocalDate today = LocalDate.now();
		LocalDate beyondFiveDays = today.plusDays(6);
		String beyondFiveDaysStr = beyondFiveDays.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			DateTextUtils.countDaysFromToday(beyondFiveDaysStr);
		});
		assertEquals(ErrorConstants.DAYS_ERROR, exception.getMessage());
	}

}
