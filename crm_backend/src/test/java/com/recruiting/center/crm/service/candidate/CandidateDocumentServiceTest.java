package com.recruiting.center.crm.service.candidate;

import com.recruiting.center.crm.annotations.IT;
import com.recruiting.center.crm.database.IntegrationTestsDatabase;
import com.recruiting.center.crm.entity.candidate.Candidate;
import com.recruiting.center.crm.entity.candidate.CandidateDocument;
import com.recruiting.center.crm.repository.candidate.CandidateDocumentsRepository;
import com.recruiting.center.crm.service.servicexceptions.DocumentNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.annotation.Rollback;

import javax.print.Doc;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@IT
@Transactional
class CandidateDocumentServiceTest extends IntegrationTestsDatabase {
    @Autowired
    private CandidateDocumentService candidateDocumentService;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private CandidateDocumentsRepository candidateDocumentsRepository;

    @Test
    void findByCandidateId() {

        assertEquals(3, candidateDocumentService.findByCandidateId(100L).size());
    }

    @Test
    void notFoundByCandidateId() {
        assertEquals(0, candidateDocumentService.findByCandidateId(1L).size());
    }

    @Test
    void findByDocumentId() {
        CandidateDocument byDocumentId = candidateDocumentService.findByDocumentId(100L);
        assertNotNull(byDocumentId);
    }

    @Test
    void notFoundByDocumentId() {
        assertThrows(DocumentNotFoundException.class, () -> candidateDocumentService.findByDocumentId(1L));
    }

    @Test
    @Rollback
    void delete() {
        List<CandidateDocument> beforeDeletion = candidateDocumentsRepository.findAll();
        CandidateDocument byDocumentId = candidateDocumentService.findByDocumentId(107L);
        candidateDocumentService.delete(byDocumentId);
        List<CandidateDocument> afterDeletion = candidateDocumentsRepository.findAll();
        assertEquals(beforeDeletion.size() - 1, afterDeletion.size());
    }

    @Test
    void deleteNonExistingDocument() {
        CandidateDocument byDocumentId = candidateDocumentService.findByDocumentId(100L);
        byDocumentId.setId(200L);
        System.out.println("DOCUMENT ID "+byDocumentId.getId());

        assertThrows(JpaSystemException.class, () -> candidateDocumentService.delete(byDocumentId));
    }

    @Test
    @Rollback
    void addDocument() {
        int sizeBefore = candidateDocumentsRepository.findAll().size();
        CandidateDocument build = CandidateDocument.builder()
                .type("Passport")
                .fileName("passport-list2.pdf")
                .filePath("/documents/candidates/109/passport_1.pdf")
                .uploadedDate(LocalDate.now())
                .fileType("application/pdf")
                .candidate(candidateService.findCandidateById(103L))
                .uploadedBy("recruiter")
                .build();
        candidateDocumentService.addDocument(build);
        int sizeAfter = candidateDocumentsRepository.findAll().size();

        assertEquals(sizeBefore+1, sizeAfter);
    }

    @Test
    void addNonValidDocument() {
        CandidateDocument build = CandidateDocument.builder()
                .type("Passport")
                .fileName("passport-list2.pdf")
                .filePath("/documents/candidates/109/passport_1.pdf")
                .uploadedDate(LocalDate.now())
                .fileType("application/pdf")
                .candidate(candidateService.findCandidateById(103L))
                .build();

        assertThrows(ValidationException.class, () -> candidateDocumentService.addDocument(build));

    }
}