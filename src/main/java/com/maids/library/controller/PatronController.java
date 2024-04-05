package com.maids.library.controller;

import com.maids.library.entity.Patron;
import com.maids.library.service.PatronService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {
    @Autowired
    private PatronService patronService;

    @GetMapping
    public ResponseEntity<List<Patron>> getAllPatrons() {
        List<Patron> patrons = patronService.findAll();
        return ResponseEntity.ok(patrons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable Long id) {
        Patron patron = patronService.findById(id); // This now throws if not found
        return ResponseEntity.ok(patron);
    }

    @PostMapping
    public ResponseEntity<Patron> createPatron(@Valid @RequestBody Patron patron) {
        Patron savedPatron=patronService.save(patron);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPatron.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedPatron);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patron> updatePatron(@Valid @PathVariable Long id, @Valid @RequestBody Patron patronDetails) {
        // findById throws PatronNotFoundException if not found
        Patron patron = patronService.findById(id);
        patron.setName(patronDetails.getName());
        patron.setContactInformation(patronDetails.getContactInformation());
        Patron updatedPatron=patronService.save(patron);
        return ResponseEntity.ok(updatedPatron);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatron(@Valid @PathVariable Long id) {
        patronService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}