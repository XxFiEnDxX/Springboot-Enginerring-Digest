package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll(){
        return journalEntryService.getAll();
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return true;
    }

    @GetMapping("/id/{myId}")
    public JournalEntry getJournalEntry(@PathVariable ObjectId myId){
        return journalEntryService.findById(myId).orElse(null);
    }

    @DeleteMapping("/id/{myId}")
    public void deleteJournalEntry(@PathVariable ObjectId myId){
        journalEntryService.deleteById(myId);
    }

    @PutMapping("/id/{myId}")
    public Boolean updateJournalEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry updateEntry){
        JournalEntry old = journalEntryService.findById(myId).orElse(null);
        if(old != null){
            old.setTitle((updateEntry.getTitle() != null && !updateEntry.getTitle().isEmpty()) ? updateEntry.getTitle(): old.getTitle());
            old.setContent((updateEntry.getContent() != null && !updateEntry.getContent().isEmpty()) ? updateEntry.getContent(): old.getContent());
            journalEntryService.saveEntry(old);
            return true;
        }
        return false;
    }
}











