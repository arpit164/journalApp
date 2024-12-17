package com.asworld.journalApp.repository;

import com.asworld.journalApp.entity.ConfigJournalAppEntity;
import com.asworld.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {
}

