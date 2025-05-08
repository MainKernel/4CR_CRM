package com.recruiting.center.crm.service.candidate;

import com.recruiting.center.crm.annotations.IT;
import com.recruiting.center.crm.database.IntegrationTestsDatabase;
import com.recruiting.center.crm.entity.candidate.CandidateDocument;
import com.recruiting.center.crm.repository.candidate.CandidateDocumentsRepository;
import com.recruiting.center.crm.service.servicexceptions.DocumentNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@IT
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
    void delete() {
        List<CandidateDocument> beforeDeletion = candidateDocumentsRepository.findAll();
        CandidateDocument byDocumentId = candidateDocumentService.findByDocumentId(100L);
        candidateDocumentService.delete(byDocumentId);
        List<CandidateDocument> afterDeletion = candidateDocumentsRepository.findAll();
        assertEquals(beforeDeletion.size() - 1, afterDeletion.size());
    }

    @Test
    void deleteNonExistingDocument() {

    }

    @Test
    void add() {
    }

    @Test
    void addNonValidDocument() {

    }
}