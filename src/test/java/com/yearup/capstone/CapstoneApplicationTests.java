package com.yearup.capstone;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"spring.datasource.url=jdbc:mysql://database1.chik2ww0m0iy.us-east-1.rds.amazonaws.com:3306/capstone",
		"spring.datasource.username=sharnajh",
		"spring.datasource.password=zi6<1T>%XryW]BY#R-8K$Bxfc#0x"
})
class CapstoneApplicationTests {

	@Test
	void contextLoads() {
	}

}
