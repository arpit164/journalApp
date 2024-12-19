package com.asworld.journalApp;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JournalAppApplicationTests {

	@Disabled
	@Test
	void contextLoads() {
		assertEquals(4, 2+2);
	}

}
