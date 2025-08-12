package com.example.JournalApp.services;

import com.example.JournalApp.entity.JournalEntries;
import com.example.JournalApp.entity.UserEntry;
import com.example.JournalApp.repository.JournalEntryRepository;
import com.example.JournalApp.repository.UserEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UserEntryService {

    @Autowired
    private UserEntryRepository userEntryRepository;


    public void saveEntry(UserEntry userEntry){
        try {
            userEntryRepository.save(userEntry);
        } catch (Exception e) {
            System.out.println("Exception"+e);
        }

    }

    public List<UserEntry> getAll() {
        return userEntryRepository.findAll();
    }

    public Optional<UserEntry> getById(ObjectId myId) {
        return userEntryRepository.findById(myId);
    }

    public void deleteById(ObjectId myId) {
        userEntryRepository.deleteById(myId);
    }

    public UserEntry findByUserName(String username){
        return userEntryRepository.findByUsername(username);
    }
}
