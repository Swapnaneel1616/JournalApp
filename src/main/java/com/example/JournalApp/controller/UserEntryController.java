package com.example.JournalApp.controller;


import com.example.JournalApp.entity.UserEntry;
import com.example.JournalApp.services.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
public class UserEntryController {

    @Autowired
    private UserEntryService userEntryService;


    @GetMapping
    public List<UserEntry> getAllUsers(){
        return userEntryService.getAll();
    }

    @PostMapping
    public boolean saveUsers(@RequestBody UserEntry myEntry){
        userEntryService.saveEntry(myEntry);
        return true;
    }


    @PutMapping(path = "/{username}")
    public ResponseEntity<?> updateDetails(@RequestBody UserEntry userEntry , @PathVariable String username){
        UserEntry userInDb = userEntryService.findByUserName(username);
        if(userInDb!=null){
            userInDb.setUsername(userEntry.getUsername());
            userInDb.setPassword(userEntry.getPassword());
            userEntryService.saveEntry(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
