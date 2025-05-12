package com.recruiting.center.crm.service.candidate;

import com.recruiting.center.crm.annotations.IT;
import com.recruiting.center.crm.database.IntegrationTestsDatabase;
import com.recruiting.center.crm.entity.candidate.PsychologicalTest;
import com.recruiting.center.crm.service.servicexceptions.PsychologicalTestNotFound;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@IT
@Transactional
class PsychologyTestServiceTest extends IntegrationTestsDatabase {
    @Autowired
    private PsychologyTestService psychologyTestService;

    @Test
    void findByStatus() {
        assertNotNull(psychologyTestService.findByStatus("Не проходив"));
    }

    @Test
    void notFoundByStatus(){
        assertThrows(PsychologicalTestNotFound.class, () -> psychologyTestService.findByStatus("Not existing status"));
    }

    @Test
    void findTestStatusById() {
        assertNotNull(psychologyTestService.findTestStatusById(100L));
    }

    @Test
    void notFoundById() {
        assertThrows(PsychologicalTestNotFound.class, () -> psychologyTestService.findTestStatusById(99L));
    }

    @Test
    void findAllPsychologicalTestStatuses() {
        assertEquals(5, psychologyTestService.findAllPsychologicalTestStatuses().size());
    }

    @Test
    @Rollback
    void deleteTestStatus() {
        int beforeDeletion = psychologyTestService.findAllPsychologicalTestStatuses().size();
        psychologyTestService.deleteTestStatus(psychologyTestService.findTestStatusById(104L));
        int afterDeletion = psychologyTestService.findAllPsychologicalTestStatuses().size();
        assertEquals(beforeDeletion-1, afterDeletion);
    }

    @Test
    void deleteNonExistingStatus(){
        PsychologicalTest testStatusById = psychologyTestService.findTestStatusById(100L);
        testStatusById.setId(123L);
        assertThrows(JpaSystemException.class, () ->
                psychologyTestService.deleteTestStatus(testStatusById));
    }

    @Test
    @Rollback
    void saveTestStatus() {
        int beforeSaving = psychologyTestService.findAllPsychologicalTestStatuses().size();
        psychologyTestService.saveTestStatus(PsychologicalTest.builder()
                        .status("Test status")
                        .color("F20000")
                .build());
        int afterSaving = psychologyTestService.findAllPsychologicalTestStatuses().size();
        assertEquals(beforeSaving + 1, afterSaving);
    }

    @Test
    void saveNonValidStatus(){
        assertThrows(ValidationException.class, () ->
                psychologyTestService.saveTestStatus(PsychologicalTest.builder()
                                .status("Test")
                        .build()));
    }
}