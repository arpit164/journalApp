package com.asworld.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    void general() {
        assertEquals(4, 2+2);
    }

    @Test
    void testSendMail(){
        emailService.sendEmail(
                "kicom13448@rabitex.com",
                "Testing Java mail sender",
                "Hii, How are you !"
        );
    }
}
