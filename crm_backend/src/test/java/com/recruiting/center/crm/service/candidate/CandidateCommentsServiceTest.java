package com.recruiting.center.crm.service.candidate;

import com.recruiting.center.crm.annotations.IT;
import com.recruiting.center.crm.database.IntegrationTestsDatabase;
import com.recruiting.center.crm.entity.candidate.CandidateComment;
import com.recruiting.center.crm.repository.candidate.CandidateCommentsRepository;
import com.recruiting.center.crm.repository.candidate.CandidateRepository;
import com.recruiting.center.crm.service.servicexceptions.CommentNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@IT
class CandidateCommentsServiceTest extends IntegrationTestsDatabase {
    @Autowired
    private CandidateCommentsService candidateCommentsService;
    @Autowired
    private CandidateCommentsRepository commentsRepository;
    @Autowired
    private CandidateService candidateService;

    @Test
    void findById() {
        assertNotNull(candidateCommentsService.findById(100L));

    }

    @Test
    void notFoundById() {
        assertThrows(CommentNotFoundException.class, () -> candidateCommentsService.findById(99L));
    }

    @Test
    void findByCandidateId() {
        assertEquals(2, candidateCommentsService.findByCandidateId(100L).size());
    }

    @Test
    void notFoundByCandidateId() {
        assertEquals(0, candidateCommentsService.findByCandidateId(1L).size());
    }


    @Test
    void deleteComment() {
        List<CandidateComment> beforeDeletion = commentsRepository.findAll();
        CandidateComment byId = candidateCommentsService.findById(101L);
        candidateCommentsService.deleteComment(byId);
        List<CandidateComment> afterDeletion = commentsRepository.findAll();

        assertEquals(beforeDeletion.size() - 1, afterDeletion.size());
    }

    @Test
    void deleteNonExistingComment() {
        CandidateComment first = candidateService.findCandidateById(100L).getComments().getFirst();
        first.setId(10L);
        assertThrows(IllegalArgumentException.class, () -> candidateCommentsService.deleteComment(first));
    }

    @Test
    void saveComment() {
        List<CandidateComment> beforeSaving = commentsRepository.findAll();
        candidateCommentsService.saveComment(CandidateComment.builder()
                .candidate(candidateService.findCandidateById(100L))
                .commentedBy("Test")
                .commentDate(LocalDate.now())
                .comment("Test comment")
                .build());
        List<CandidateComment> afterSaving = commentsRepository.findAll();
        assertEquals(beforeSaving.size() + 1, afterSaving.size());
    }

    @Test
    void saveNotValidComment() {
        CandidateComment comment = null;
        assertThrows(IllegalArgumentException.class, () -> candidateCommentsService.saveComment(comment));
    }
}