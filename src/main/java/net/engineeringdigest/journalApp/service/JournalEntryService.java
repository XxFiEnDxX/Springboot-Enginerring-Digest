package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
}
