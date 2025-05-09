package com.recruiting.center.crm.service.candidate;


import com.recruiting.center.crm.entity.candidate.Candidate;
import com.recruiting.center.crm.entity.candidate.CandidateComment;
import com.recruiting.center.crm.repository.candidate.CandidateCommentsRepository;
import com.recruiting.center.crm.service.servicexceptions.CommentNotFoundException;
import com.recruiting.center.crm.service.servicexceptions.DataIntegrityConflictException;
import com.recruiting.center.crm.service.servicexceptions.DuplicatedCommentException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
@Transactional
public class CandidateCommentsService {
    private final CandidateCommentsRepository candidateCommentsRepository;
    private final CandidateService candidateService;


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

    public void saveComment(@Valid CandidateComment comment) {
        if (comment == null) {
            log.error("CandidateCommentsService: Attempt to save null");
            throw new IllegalArgumentException("Attempt to save null");
        }
        try {
            log.debug("CandidateCommentsService: Saving comment {}", comment);
            candidateCommentsRepository.save(comment);
            log.debug("CandidateCommentsService: Comment successfully saved");

        } catch (DataIntegrityViolationException ex) {
            log.error("CandidateCommentService: Error saving comment");
            throw new DuplicatedCommentException("Error saving comment", ex);
        }
    }


    public void deleteComment(@Valid CandidateComment comment) {
        if (!candidateCommentsRepository.existsById(comment.getId())) {
            log.error("CandidateCommentService: Attempt to delete non existing comment");
            throw new IllegalArgumentException("Attempt to delete non existing comment");
        }

        try {
            /*
             * Problem I found here
             * Hibernate doesn't trigger deletion from database
             * if you execute candidateCommentsRepository.deleteById(id)
             * the comment still remain in database.
             *
             * So we get candidate from comment and delete comment in List<CandidateComment>
             * after that save candidate back to database, and hibernate delete that comment
             * from database
             *
             */

            Candidate candidate = comment.getCandidate();
            candidate.getComments().remove(comment);
            candidateCommentsRepository.delete(comment);

            log.debug("CandidateCommentService: deleted comment with id {}", comment.getId());
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityConflictException("CandidateCommentService: error occurred due to comment deletion", ex);
        }
    }
}
