package com.recruiting.center.crm.service.candidate;

import com.recruiting.center.crm.entity.candidate.CandidateStatus;
import com.recruiting.center.crm.repository.candidate.CandidateStatusRepository;
import com.recruiting.center.crm.service.servicexceptions.CandidateStatusException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CandidateStatusService {
    private final CandidateStatusRepository statusRepository;

    public CandidateStatus findStatusById(Long id){
        return statusRepository.findById(id).orElseThrow(() -> {
            log.error("CandidateStatusService: Attempt to find non existing status with id {}", id);
           return new IllegalArgumentException("Attempt to find non existing status with id " + id);
        });
    }

    public List<CandidateStatus> findAllStatuses() {
        List<CandidateStatus> all = statusRepository.findAll();
        if (all.isEmpty()) {
            log.warn("CandidateStatusService: there is no statuses found");
        } else {
            log.debug("CandidateStatusService: There is {} found", all.size());
        }
        return all;
    }

    public void addCandidateStatus(@Valid CandidateStatus status) {
        try {
            statusRepository.save(status);
            log.debug("CandidateStatusService: Candidate status {} was saved", status.getStatus());
        } catch (DataIntegrityViolationException ex) {
            log.error("CandidateStatusService: Error occurred due to status save");
            throw new CandidateStatusException("CandidateStatusService: Error occurred due to status deletion", ex);
        }
    }

    public void deleteCandidateStatus(@Valid CandidateStatus status){
        if(!statusRepository.existsById(status.getId())){
            log.warn("CandidateStatusService: Attempt to delete non existing status");
            throw new IllegalArgumentException("Attempt to delete non existing status");
        }
        try{
            statusRepository.delete(status);
            log.debug("CandidateStatusService: Candidate status {} was deleted", status.getStatus());
        } catch (DataIntegrityViolationException ex){
            log.error("CandidateStatusService: Error occurred due to status deletion");
            throw new CandidateStatusException("CandidateStatusService: Error occurred due to status save ", ex);
        }
    }
}
