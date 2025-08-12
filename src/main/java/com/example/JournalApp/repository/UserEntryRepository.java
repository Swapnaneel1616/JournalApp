package com.example.JournalApp.repository;

import com.example.JournalApp.entity.JournalEntries;
import com.example.JournalApp.entity.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryRepository extends MongoRepository<UserEntry, ObjectId> {
    UserEntry findByUsername(String name);

}
