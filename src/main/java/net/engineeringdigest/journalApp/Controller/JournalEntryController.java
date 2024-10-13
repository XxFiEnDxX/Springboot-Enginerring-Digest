package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("journal")
public class JournalEntryController {

    public Map<Long, JournalEntry> JournalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(JournalEntries.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){
        JournalEntries.put(myEntry.getId(), myEntry);
        return true;
    }

    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntry(@PathVariable Long myId){
        return JournalEntries.get(myId);
    }

    @DeleteMapping("id/{myId}")
    public JournalEntry deleteJournalEntry(@PathVariable Long myId){
        return JournalEntries.remove(myId);
    }

    @PutMapping("id/{ids}")
    public void updateJournalEntry(@PathVariable Long ids, @RequestBody JournalEntry myEntry){
        try{
            JournalEntries.put(ids, myEntry);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}











