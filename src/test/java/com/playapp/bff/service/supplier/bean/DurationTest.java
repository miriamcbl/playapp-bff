package com.playapp.bff.service.supplier.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class DurationTest {

	@Test
	void gettersAndSettersTest() {
        Duration duration = new Duration();
		String text = "5 mins";
		int value = 5;
        duration.setText(text);
        duration.setValue(value);
        assertEquals(text, duration.getText());
        assertEquals(value, duration.getValue());
    }

    @Test
	void builderTest() {
		String text = "5 mins";
		int value = 5;
        Duration duration = Duration.builder()
                .text(text)
                .value(value)
                .build();
        assertEquals(text, duration.getText());
        assertEquals(value, duration.getValue());
    }

    @Test
	void allArgsConstructorTest() {
		String text = "5 mins";
		int value = 5;
        Duration duration = new Duration(text, value);
        assertEquals(text, duration.getText());
        assertEquals(value, duration.getValue());
    }

    @Test
	void noArgsConstructorTest() {
        Duration duration = new Duration();
        assertNull(duration.getText());
        assertEquals(0, duration.getValue());
    }

}
