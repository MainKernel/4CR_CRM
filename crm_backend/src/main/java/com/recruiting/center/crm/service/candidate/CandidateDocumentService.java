package com.recruiting.center.crm.service.candidate;

import com.recruiting.center.crm.entity.candidate.CandidateDocument;
import com.recruiting.center.crm.repository.candidate.CandidateDocumentsRepository;
import com.recruiting.center.crm.service.servicexceptions.DataIntegrityConflictException;
import com.recruiting.center.crm.service.servicexceptions.DocumentNotFoundException;
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
public class CandidateDocumentService {
    private final CandidateDocumentsRepository documentsRepository;

    public List<CandidateDocument> findByCandidateId(Long id) {
        List<CandidateDocument> documents = documentsRepository.findByCandidateId(id);

        if (documents.isEmpty()) {
            log.error("CandidateDocumentService: no documents related to candidate with id {}", id);
        } else {
            log.debug("CandidateDocumentService: Found {} documents related to candidate with id {}", documents.size(), id);
        }

        return documents;
    }

    public CandidateDocument findByDocumentId(Long id) {
        return documentsRepository.findById(id).orElseThrow(() -> {
            log.error("CandidateDocumentService: No document with id {}", id);
            return new DocumentNotFoundException("CandidateDocumentService: No document with id " + id);
        });
    }

    public void delete(@Valid CandidateDocument document) {
        if (!documentsRepository.existsById(document.getId())) {
            log.warn("CandidateDocumentService: no document exists with id {}", document.getId());
            throw new IllegalArgumentException("CandidateDocumentService: no document exists");
        }

        try {
            documentsRepository.delete(document);
            log.debug("CandidateDocumentService: document was successfully deleted");
        } catch (DataIntegrityViolationException ex) {
            log.error("CandidateDocumentService: Error occurred due to document deletion");
            throw new DataIntegrityConflictException("CandidateDocumentService: Error occurred due to document deletion", ex);
        }
    }

    public void add(@Valid CandidateDocument document) {
        try {
            documentsRepository.save(document);
            log.debug("CandidateDocumentService: document was successfully saved");
        } catch (DataIntegrityViolationException ex) {
            log.error("CandidateDocumentService: error occurred due to saving");
            throw new DataIntegrityConflictException("CandidateDocumentService: error occurred due to saving", ex);
        }
    }
}
