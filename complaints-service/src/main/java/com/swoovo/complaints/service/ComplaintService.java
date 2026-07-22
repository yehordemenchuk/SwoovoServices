package com.swoovo.complaints.service;

import com.swoovo.complaints.dto.ComplaintRequest;
import com.swoovo.complaints.dto.ComplaintResponse;
import com.swoovo.complaints.mapper.ComplaintMapper;
import com.swoovo.complaints.repository.ComplaintRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final ComplaintMapper complaintMapper;

    @Transactional
    public ComplaintResponse createComplaint(ComplaintRequest complaintRequest) {

    }
}
