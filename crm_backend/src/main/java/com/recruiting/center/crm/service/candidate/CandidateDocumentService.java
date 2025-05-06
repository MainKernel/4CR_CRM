package com.recruiting.center.crm.service.candidate;

import com.recruiting.center.crm.entity.candidate.CandidateDocument;
import com.recruiting.center.crm.repository.candidate.CandidateDocumentsRepository;
import com.recruiting.center.crm.service.servicexceptions.DocumentNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
