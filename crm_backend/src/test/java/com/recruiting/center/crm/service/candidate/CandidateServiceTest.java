package com.recruiting.center.crm.service.candidate;

import com.recruiting.center.crm.annotations.IT;
import com.recruiting.center.crm.database.IntegrationTestsDatabase;
import com.recruiting.center.crm.entity.candidate.Candidate;
import com.recruiting.center.crm.repository.candidate.CandidatePagingRepository;
import com.recruiting.center.crm.repository.candidate.CandidateRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@IT
@Transactional
class CandidateServiceTest extends IntegrationTestsDatabase {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private CandidatePagingRepository pagingRepository;
    @Autowired
    private CandidateStatusService candidateStatusService;
    @Autowired
    private DutyTypeService dutyTypeService;
    @Autowired
    private MilitaryUnitService militaryUnitService;
    @Autowired
    private PsychologyTestService psychologyTestService;
    @Autowired
    private CandidateRepository candidateRepository;

    @Test
    void findCandidateById() {
        assertNotNull(candidateService.findCandidateById(100L));
    }

    @Test
    void findByPhoneNumber() {
        assertNotNull(candidateService.findByPhoneNumber("+380991234567"));
    }

    @Test
    void findByRecruiter() {
        assertNotNull(candidateService.findByRecruiter("Марія Іванова"));
    }

    @Test
    void findByCurator() {
        assertNotNull(candidateService.findByCurator("Олександр Петренко"));
    }

    @Test
    void findByEscortedBy() {
        assertNotNull(candidateService.findByEscortedBy("Василь Сидоренко"));
    }

    @Test
    void findByRecommendationLetterDate() {
        assertNotNull(candidateService.findByRecommendationLetterDate(LocalDate.of(2023, 1, 15)));
    }

    @Test
    void findPageByRecruiterId() {
        Page<Candidate> pageByRecruiter = candidateService.findPageByRecruiter("Марія Іванова", Pageable.ofSize(10));
        assertEquals(10, pageByRecruiter.getContent().size());
    }

    @Test
    void findPageByCurator() {
        Page<Candidate> pageByCurator = candidateService.findPageByCurator("Олександр Петренко", PageRequest.of(0, 10));
        assertEquals(10, pageByCurator.getContent().size());
    }

    @Test
    void findPageByRecommendationLetter() {
        Page<Candidate> pageByRecommendationLetter = candidateService.findPageByRecommendationLetter(LocalDate.of(2023, 4, 5), Pageable.ofSize(10));
        assertEquals(1, pageByRecommendationLetter.getContent().size());
    }

    @Test
    void findPageByEscortedBy() {
        Page<Candidate> pageByEscortedBy = candidateService.findPageByEscortedBy("Василь Сидоренко", PageRequest.of(0, 10));
        assertEquals(10, pageByEscortedBy.getContent().size());
    }

    @Test
    void findPageByPhoneNumber() {
        Page<Candidate> pageByPhoneNumber = candidateService.findPageByPhoneNumber("+380993456789", PageRequest.of(0, 10));
        assertEquals(1, pageByPhoneNumber.getContent().size());
    }

    @Test
    @Rollback
    void deleteCandidate() {

    }

    @Test
    void deleteNonExistingCandidate() {

    }

    @Test
    @Rollback
    void addCandidate() {
       Candidate nonExistingCandidate = Candidate.builder()
                .surname("Test")
                .name("Test")
                .middleName("Test")
                .status(candidateStatusService.findStatusById(100L))
                .phoneNumber("+380674813449")
                .dutyType(dutyTypeService.findDutyTypeById(100L))
                .militaryUnit(militaryUnitService.findById(100L))
                .recommendationLetter(LocalDate.now())
                .recruiter("Test recruiter")
                .curator("Test curator")
                .psychologicalTest(psychologyTestService.findTestStatusById(100L))
                .build();
        int beforeAdding = candidateRepository.findAll().size();
        candidateService.addCandidate(nonExistingCandidate);
        int afterAdding = candidateRepository.findAll().size();
        assertEquals(beforeAdding + 1, afterAdding);
    }

    @Test
    void addNonValidCandidate() {
       Candidate nonExistingCandidate = Candidate.builder()
                .surname("Test")
                .middleName("Test")
                .status(candidateStatusService.findStatusById(100L))
                .phoneNumber("+380674813449")
                .dutyType(dutyTypeService.findDutyTypeById(100L))
                .militaryUnit(militaryUnitService.findById(100L))
                .recommendationLetter(LocalDate.now())
                .recruiter("Test recruiter")
                .curator("Test curator")
                .psychologicalTest(psychologyTestService.findTestStatusById(100L))
                .build();
        assertThrows(ValidationException.class,() -> candidateService.addCandidate(nonExistingCandidate));
    }
}