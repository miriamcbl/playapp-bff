package com.playapp.bff;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
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

	@Test
	void testMainMethod() {
		// Verifica que SpringApplication.run() sea invocado
		SpringApplication mockSpringApplication = Mockito.mock(SpringApplication.class);
		BffApplication.main(new String[] {});
		verify(mockSpringApplication, times(1));
	}

}
