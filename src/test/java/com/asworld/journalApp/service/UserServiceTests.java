package com.asworld.journalApp.service;

import com.asworld.journalApp.entity.User;
import com.asworld.journalApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Disabled
    @Test
    public void testFindByUserName(){
//        assertEquals(4, 2+2);
//        assertNotNull(userRepository.findByUserName("ram"));
        User user = userRepository.findByUserName("ram");
//        assertTrue(!user.getJournalEntries().isEmpty());
        assertFalse(user.getJournalEntries().isEmpty());
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",//"a, b, expected"
            "2,10,12",
            "3,3,9"
    })
    public void test(int a, int b, int expected){
        assertEquals(expected, a + b);
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {
            "ram",//"a, b, expected"
            "shyam",
            "arpit"
    })
    public void parameterizedTestFindByUserName(String uname){
        assertNotNull(userRepository.findByUserName(uname), "failed for: " + uname);
    }

    @Disabled
    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void parameterizedTestArgsSaveNewUser(User user){
        assertTrue(userService.saveNewUser(user));
    }
}
