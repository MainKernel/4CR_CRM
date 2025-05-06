package com.recruiting.center.crm.service.candidate;


import com.recruiting.center.crm.entity.candidate.CandidateComment;
import com.recruiting.center.crm.repository.candidate.CandidateCommentsRepository;
import com.recruiting.center.crm.service.servicexceptions.CommentNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CandidateCommentsService {
    private final CandidateCommentsRepository candidateCommentsRepository;


    public CandidateComment findById(Long id) {
        return candidateCommentsRepository.findById(id).orElseThrow(() -> {
            log.error("CandidateCommentsService: No comment with id {}", id);
            return new CommentNotFoundException("No comment with id " + id);
        });
    }

    public List<CandidateComment> findByCandidateId(Long id) {
        List<CandidateComment> comments = candidateCommentsRepository.findByCandidateId(id);

        if (comments.isEmpty()) {
            log.warn("CandidateCommentsService: No comments found by candidate id {}", id);
        } else {
            log.debug("CandidateCommentsService: Found {} results with user id {}", comments.size(), id);
        }
        return comments;
    }
}
