package com.recruiting.center.crm.service.candidate;

import com.recruiting.center.crm.entity.candidate.CandidatePosition;
import com.recruiting.center.crm.repository.candidate.CandidatePositionPagingRepository;
import com.recruiting.center.crm.repository.candidate.CandidatePositionRepository;
import com.recruiting.center.crm.service.servicexceptions.CandidatePositionNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CandidatePositionService {
    private final CandidatePositionPagingRepository pagingRepository;
    private final CandidatePositionRepository candidatePositionRepository;

    public CandidatePosition getAllCandidatePositionById(Long id){
        return candidatePositionRepository.findById(id).orElseThrow( () -> {
            log.debug("CandidatePositionService: Candidate not found");
        return new CandidatePositionNotFoundException("CandidateService: Candidate");
        });
    }

    public List<CandidatePosition> findAll() {
        return candidatePositionRepository.findAll();
    }

}
