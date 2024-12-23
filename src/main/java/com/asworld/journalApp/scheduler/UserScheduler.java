package com.asworld.journalApp.scheduler;

import com.asworld.journalApp.cache.AppCache;
import com.asworld.journalApp.entity.JournalEntry;
import com.asworld.journalApp.entity.User;
import com.asworld.journalApp.enums.Sentiment;
import com.asworld.journalApp.model.SentimentData;
import com.asworld.journalApp.repository.UserRepositoryImpl;
import com.asworld.journalApp.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private AppCache appCache;

    @Autowired
    KafkaTemplate<String, SentimentData> kafkaTemplate;

//    @Scheduled(cron = "0 0 9 * * SUN") //send email on every sunday 9am
//    @Scheduled(cron = "0 * * ? * *") //send mail after every minute
    public void fetchUsersAndSendSaMail(){
        List<User> users = userRepository.getUserForSA();
        for (User user : users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate()
                    .isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getSentiment())
                    .collect(Collectors.toList());

            Map<Sentiment, Integer> sentimentCount = new HashMap<>();
            for (Sentiment sentiment : sentiments){
                if (sentiment != null)
                    sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment, 0) + 1);
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCount.entrySet()){
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if (mostFrequentSentiment != null){
                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days of user:" + user.getUserName() + mostFrequentSentiment).build();
                try {
                    kafkaTemplate.send("weekly-sentiment", sentimentData.getEmail(), sentimentData);
                }
                catch (Exception e){
                    emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days of user:" + user.getUserName() , mostFrequentSentiment.toString());
                    log.info("Mail sended to user:" + user.getUserName() + " Email:" + user.getEmail());
                }
            }
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *") //run every 10 mins
    public void clearAppCache(){
        appCache.init();
    }
}
