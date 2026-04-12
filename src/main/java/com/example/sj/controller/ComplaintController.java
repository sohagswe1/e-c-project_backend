package com.example.sj.controller;

import com.example.sj.entity.Complaint;
import com.example.sj.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {
    
    @Autowired
    private ComplaintService complaintService;
    
    @PostMapping
    public ResponseEntity<Complaint> createComplaint(@RequestBody Complaint complaint) {
        return new ResponseEntity<>(complaintService.save(complaint), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Complaint>> getComplaintById(@PathVariable Integer id) {
        return ResponseEntity.ok(complaintService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Complaint> updateComplaint(@PathVariable Integer id, @RequestBody Complaint complaint) {
        return ResponseEntity.ok(complaintService.update(id, complaint));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComplaint(@PathVariable Integer id) {
        complaintService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
