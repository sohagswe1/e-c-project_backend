package com.example.sj.service_implement;

import com.example.sj.entity.Complaint;
import com.example.sj.repository.ComplaintRepository;
import com.example.sj.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintServiceImplement implements ComplaintService {
    
    @Autowired
    private ComplaintRepository complaintRepository;
    
    @Override
    public Complaint save(Complaint complaint) {
        return complaintRepository.save(complaint);
    }
    
    @Override
    public Optional<Complaint> findById(Integer id) {
        return complaintRepository.findById(id);
    }
    
    @Override
    public List<Complaint> findAll() {
        return complaintRepository.findAll();
    }
    
    @Override
    public Complaint update(Integer id, Complaint complaint) {
        return complaintRepository.findById(id).map(existingComplaint -> {
            Complaint updatedComplaint = Complaint.builder()
                    .id(existingComplaint.getId())
                    .user(complaint.getUser())
                    .order(complaint.getOrder())
                    .subject(complaint.getSubject())
                    .message(complaint.getMessage())
                    .status(complaint.getStatus())
                    .createdAt(complaint.getCreatedAt())
                    .build();
            return complaintRepository.save(updatedComplaint);
        }).orElseThrow(() -> new RuntimeException("Complaint not found"));
    }
    
    @Override
    public void delete(Integer id) {
        complaintRepository.deleteById(id);
    }
}
