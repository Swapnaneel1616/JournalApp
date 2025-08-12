package com.example.JournalApp.controller;

import com.example.JournalApp.entity.JournalEntries;
import com.example.JournalApp.entity.UserEntry;
import com.example.JournalApp.services.JournalEntryService;
import com.example.JournalApp.services.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserEntryService userEntryService;

    @GetMapping(path = "{username}")
    public ResponseEntity<?> getAllEntriesOfUsers(@PathVariable String username){
        UserEntry user = userEntryService.findByUserName(username);

        List<JournalEntries> check = user.getJournalEntries();
        if(check!=null && !check.isEmpty()){
            return new ResponseEntity<>(check , HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "{username}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntries myEntry ,  @PathVariable String username ){
        try{

            journalEntryService.saveEntry(myEntry , username);
            return new ResponseEntity<>(myEntry , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(path = "id/{myId}")
    public ResponseEntity<?> getById(@PathVariable ObjectId myId){
         Optional<JournalEntries> check = journalEntryService.getById(myId);
         if(check.isPresent()){
             return new ResponseEntity<>(check.get(), HttpStatus.OK);
         }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "id/{username}/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId , @PathVariable String username){
        journalEntryService.deleteById(myId , username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "id/{username}/{myId}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId myId ,
                                        @RequestBody JournalEntries newEntry,
                                        @PathVariable String username){

        JournalEntries check  = journalEntryService.getById(myId).orElse(null);
        if(check!=null){
            check.setTitle(newEntry.getTitle()!= null && !newEntry.getTitle().equals("")?newEntry.getTitle(): check.getTitle());
            check.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")?newEntry.getContent():check.getContent());
            journalEntryService.saveEntry(check);
            return new ResponseEntity<>(check , HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
