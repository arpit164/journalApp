package com.asworld.journalApp.repository;

import com.asworld.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.schema.JsonSchemaObject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA(){
        Query query = new Query();
//        query.addCriteria(Criteria.where("userName").is("arpit"));

//        //for set that one of the criteria run - orOperator()
//        Criteria criteria = new Criteria();
//        query.addCriteria(criteria.orOperator(
//                Criteria.where("email").exists(true),
//                Criteria.where("sentimentAnalysis").is(true))
//        );

        //validate email by regex
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9._]+\\.[A-Z|a-z]{2,6}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));

        //give users whos roles are USER or ADMIN
        query.addCriteria(Criteria.where("roles").in("USER", "ADMIN"));

        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }
}

