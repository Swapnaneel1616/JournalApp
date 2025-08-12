package com.example.JournalApp.services;

import com.example.JournalApp.entity.JournalEntries;
import com.example.JournalApp.entity.UserEntry;
import com.example.JournalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserEntryService userEntryService;

    @Transactional
    public void saveEntry(JournalEntries journalEntries, String username){
        try {
            UserEntry userEntry = userEntryService.findByUserName(username);
            journalEntries.setDate(LocalDateTime.now());
            JournalEntries saved = journalEntryRepository.save(journalEntries);
            userEntry.getJournalEntries().add(saved);
            userEntryService.saveEntry(userEntry);
        } catch (Exception e) {
            System.out.println("Exception"+e);
        }

    }
    public void saveEntry(JournalEntries journalEntries){
        journalEntryRepository.save(journalEntries);

    }

    public List<JournalEntries> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntries> getById(ObjectId myId) {
        return journalEntryRepository.findById(myId);
    }

    public void deleteById(ObjectId myId, String username) {
        UserEntry userEntry = userEntryService.findByUserName(username);
        userEntry.getJournalEntries().removeIf(x -> x.getId().equals(myId));
        userEntryService.saveEntry(userEntry);
        journalEntryRepository.deleteById(myId);
    }
}
