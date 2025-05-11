package com.recruiting.center.crm.service.candidate;

import com.recruiting.center.crm.annotations.IT;
import com.recruiting.center.crm.database.IntegrationTestsDatabase;
import com.recruiting.center.crm.entity.candidate.CandidateStatus;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.TransactionSystemException;

import static org.junit.jupiter.api.Assertions.*;

@IT
class CandidateStatusServiceTest extends IntegrationTestsDatabase {
    @Autowired
    private CandidateStatusService candidateStatusService;

    @Test
    void findStatusById() {
        assertNotNull(candidateStatusService.findStatusById(100L));
    }

    @Test
    void findByNonExistingId() {
        assertThrows(IllegalArgumentException.class, () -> candidateStatusService.findStatusById(99L));
    }

    @Test
    void findAllStatuses() {
        int size = candidateStatusService.findAllStatuses().size();
        assertEquals(11, size);
    }

    @Test
    @Rollback
    void addCandidateStatus() {
        int beforeAdding = candidateStatusService.findAllStatuses().size();
        candidateStatusService.addCandidateStatus(CandidateStatus.builder()
                        .status("Test")
                        .color("C20000")
                .build());
        int afterAdding = candidateStatusService.findAllStatuses().size();
        assertEquals(beforeAdding + 1, afterAdding);
    }

    @Test
    void addNonValidStatus() {
        assertThrows(TransactionSystemException.class, () -> candidateStatusService.addCandidateStatus(CandidateStatus.builder()
                        .status("Test")
                .build()));
    }

    @Test
    @Rollback
    void deleteCandidateStatus() {
        candidateStatusService.addCandidateStatus(CandidateStatus.builder()
                        .status("Test status")
                        .color("C20000")
                .build());
        int beforeDeletion = candidateStatusService.findAllStatuses().size();
        candidateStatusService.deleteCandidateStatus(candidateStatusService.findStatusById(111L));
        int afterDeletion = candidateStatusService.findAllStatuses().size();
        assertEquals(beforeDeletion-1, afterDeletion);
    }

    @Test
    void deleteNonValidStatus() {
        CandidateStatus statusById = candidateStatusService.findStatusById(100L);
        statusById.setId(300L);
        assertThrows(ObjectOptimisticLockingFailureException.class, () -> candidateStatusService.addCandidateStatus(statusById));
    }
}