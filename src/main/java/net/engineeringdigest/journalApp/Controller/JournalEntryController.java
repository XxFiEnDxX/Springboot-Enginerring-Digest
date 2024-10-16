package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntry(){
        List<JournalEntry> all = journalEntryService.getAll();
        return new ResponseEntity<>(all,HttpStatus.OK);
    }

    @GetMapping("{username}")
    public ResponseEntity<?> getAllJournalEntryOfUser(@PathVariable String username){
        User user = userService.findByUserName(username);
        List<JournalEntry> all = user.getJournalEntries();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{username}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String username){
        try{
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntry(@PathVariable ObjectId myId){

        Optional<JournalEntry> data = journalEntryService.findById(myId);
        if(data.isPresent()){
            return new ResponseEntity<>(data.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{username}/{myId}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable String username, @PathVariable ObjectId myId){
        journalEntryService.deleteById(username, myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{username}/{myId}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable String username, @PathVariable ObjectId myId, @RequestBody JournalEntry updateEntry){
        JournalEntry old = journalEntryService.findById(myId).orElse(null);
        if(old != null){
            old.setTitle((updateEntry.getTitle() != null && !updateEntry.getTitle().isEmpty()) ? updateEntry.getTitle(): old.getTitle());
            old.setContent((updateEntry.getContent() != null && !updateEntry.getContent().isEmpty()) ? updateEntry.getContent(): old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}











