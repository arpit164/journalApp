package com.asworld.journalApp.controller;

import com.asworld.journalApp.cache.AppCache;
import com.asworld.journalApp.entity.User;
import com.asworld.journalApp.service.EmailService;
import com.asworld.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @Autowired
    private EmailService emailService;

    //for sending email
    @Value("${emailData.sendTo}")
    private String sendTo;
    @Value("${emailData.subject}")
    private String subject;
    @Value("${emailData.body}")
    private String body;

    @GetMapping("all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> all = userService.getAll();
        if (all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("create-admin-users")
    public void createAdminUser(@RequestBody User user){
        userService.saveAdmin(user);
    }

    @GetMapping("clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }

    @GetMapping("sendMail")
    public ResponseEntity<?> sendMail(){
        emailService.sendEmail(sendTo, subject, body);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
