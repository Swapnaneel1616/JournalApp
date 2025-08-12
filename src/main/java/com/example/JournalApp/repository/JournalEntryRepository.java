package com.example.JournalApp.repository;

import com.example.JournalApp.entity.JournalEntries;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntries , ObjectId> {

}
