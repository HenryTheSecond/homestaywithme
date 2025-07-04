package com.homestaywithme.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class HomestaywithmeApplicationTests {

	@Test
	void contextLoads() { // Noncompliant - method is empty
	}

}
