package com.swoovo.complaints.service;

import com.swoovo.complaints.dto.ComplaintRequest;
import com.swoovo.complaints.dto.ComplaintResponse;
import com.swoovo.complaints.entity.Complaint;
import com.swoovo.complaints.mapper.ComplaintMapper;
import com.swoovo.complaints.repository.ComplaintRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.swoovo.support.util.MinioUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final ComplaintMapper complaintMapper;
    private final MinioUtil minioUtil;

    @Transactional
    @CacheEvict(value = "complaint", allEntries = true)
    public ComplaintResponse createComplaint(ComplaintRequest complaintRequest) {
        Complaint complaint = complaintMapper.fromRequest(complaintRequest);

        minioUtil.uploadFile(complaintRequest.image());

        complaintRepository.save(complaint);

        return getComplaintResponse(complaint);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "complaint", key = "#id")
    public ComplaintResponse findComplaintById(long id) throws EntityNotFoundException {
        return getComplaintResponse(complaintRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new));
    }

    @Transactional(readOnly = true)
    public List<ComplaintResponse> findComplaintsByUserId(long userId) {
        return mapComplaintsToResponses(complaintRepository.findComplaintByUserId(userId));
    }

    @Transactional(readOnly = true)
    public List<ComplaintResponse> getAllComplaints() {
        return mapComplaintsToResponses(complaintRepository.findAll());
    }

    public void deleteComplaintById(long id) throws EntityNotFoundException {
        if (!complaintRepository.existsById(id)) {
            throw new EntityNotFoundException("Complaint not found");
        }

        complaintRepository.deleteById(id);
    }

    private List<ComplaintResponse> mapComplaintsToResponses(List<Complaint> complaints) {
        return complaints.stream()
                .map(this::getComplaintResponse)
                .toList();
    }

    private ComplaintResponse getComplaintResponse(Complaint complaint) {
        ComplaintResponse complaintResponse = complaintMapper.toResponse(complaint);

        complaintResponse.setImageUrl(minioUtil.downloadFile(complaint.getImageFilePath()));

        return complaintResponse;
    }
}
