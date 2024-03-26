package com.playapp.bff;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BffApplication.class)
class BffApplicationTest {

	@Autowired
	private BffApplication bffApplication;

	@Test
	void contextLoads() {
		// Comprueba que el arranque del micro sea correcto
		assertNotNull(bffApplication);
	}

}
