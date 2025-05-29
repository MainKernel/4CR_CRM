package com.recruiting.center.crm.service.candidate;

import com.recruiting.center.crm.dto.response.GetCandidatesResponseDto;
import com.recruiting.center.crm.entity.candidate.Candidate;
import com.recruiting.center.crm.entity.candidate.enums.Completed;
import com.recruiting.center.crm.repository.candidate.CandidatePagingRepository;
import com.recruiting.center.crm.repository.candidate.CandidateRepository;
import com.recruiting.center.crm.service.candidate.specifications.CandidateFullSearchSpecification;
import com.recruiting.center.crm.service.candidate.specifications.CandidateWithoutCuratorAndEscortSpecification;
import com.recruiting.center.crm.service.servicexceptions.CandidateNotFoundException;
import com.recruiting.center.crm.service.servicexceptions.DataIntegrityConflictException;
import com.recruiting.center.crm.utils.phone.PhoneValidationService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@Validated
//@CacheConfig(cacheNames = "candidates")
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final CandidatePagingRepository candidatePagingRepository;
    private final PhoneValidationService phoneValidationService;

    /**
     * Find candidates by specified role
     *
     * @param repositoryMethod repository method to execute
     * @param role             human-readable role name for logging
     * @param roleName         actual value to search for
     * @return unmodifiable list of candidates or empty list if candidates not found
     */
    private List<Candidate> findCandidates(Supplier<List<Candidate>> repositoryMethod, String role, String roleName) {
        List<Candidate> candidates = repositoryMethod.get();

        if (candidates.isEmpty()) {
            log.warn("CandidateService: Candidates with {} {} not found", role, roleName);
        } else {
            log.debug("CandidateService: Found {} candidates with {} {}", candidates.size(), role, roleName);
        }

        return Collections.unmodifiableList(candidates);
    }

    /**
     * Find candidates by specified role
     *
     * @param pagingRepositoryMethod repository method to execute
     * @param role                   human-readable role name for logging
     * @param roleName               actual value to search for
     * @return page of candidates
     */
    private Page<Candidate> findCandidatesPage(Supplier<Page<Candidate>> pagingRepositoryMethod, String role, String roleName) {
        Page<Candidate> candidates = pagingRepositoryMethod.get();

        if (candidates.isEmpty()) {
            log.warn("CandidateService: Candidates page with {} {} not found", role, roleName);
        } else {
            log.debug("CandidateService: Candidates page found {} with {} {}", candidates.getTotalElements(), role, roleName);
        }
        return candidates;
    }


    public Candidate findCandidateById(Long id) {
        return candidateRepository.findCandidateById(id).orElseThrow(() -> {
            log.error("CandidateService: Candidate with id {} not found", id);
            return new CandidateNotFoundException("CandidateService: candidate with id " + id + " not found");
        });
    }

    public List<Candidate> findByPhoneNumber(@NotBlank String phoneNumber) {
        String validatedPhoneNumber = phoneValidationService.phoneNumberValidation(phoneNumber);
        return findCandidates(
                () -> candidateRepository.findCandidateByPhoneNumber(validatedPhoneNumber),
                "phone number",
                phoneNumber
        );
    }

    public List<Candidate> findByRecruiter(@NotBlank String recruiter) {
        return findCandidates(
                () -> candidateRepository.findCandidateByRecruiter(recruiter),
                "recruiter",
                recruiter
        );
    }

    public List<Candidate> findByCurator(@NotBlank String curator) {
        return findCandidates(
                () -> candidateRepository.findCandidateByCurator(curator),
                "curator",
                curator
        );
    }

    public List<Candidate> findByEscortedBy(@NotBlank String escortedBy) {
        return findCandidates(
                () -> candidateRepository.findCandidateByEscortedBy(escortedBy),
                "escort",
                escortedBy
        );
    }

    public List<Candidate> findByRecommendationLetterDate(LocalDate date) {
        return findCandidates(
                () -> candidateRepository.findCandidateByRecommendationLetter(date),
                "date",
                date.toString()
        );
    }

    public Page<Candidate> findPageByRecruiter(String recruiter, Pageable pageable) {

        Page<Candidate> allByRecruiterId = candidatePagingRepository.findAllByRecruiter(recruiter, pageable);

        if (allByRecruiterId.isEmpty()) {
            log.warn("CandidateService: There is no candidates found with recruiter id {}", recruiter);
        } else {
            log.debug("CandidateService: There is {} candidates found for recruiter with id {}", allByRecruiterId.getTotalElements(), recruiter);
        }

        return allByRecruiterId;
    }

    public Page<Candidate> findPageByCurator(@NotBlank String curator, Pageable pageable) {
        return findCandidatesPage(() -> candidatePagingRepository.findAllByCurator(curator, pageable),
                "curator",
                curator
        );
    }

    public Page<Candidate> findPageByRecommendationLetter(LocalDate date, Pageable pageable) {
        return findCandidatesPage(() -> candidatePagingRepository.findAllByRecommendationLetter(date, pageable),
                "date",
                date.toString()
        );
    }

    public Page<Candidate> findPageByEscortedBy(@NotBlank String escortedBy, Pageable pageable) {
        return findCandidatesPage(() -> candidatePagingRepository.findAllByEscortedBy(escortedBy, pageable),
                "escort by",
                escortedBy
        );
    }

    public Page<Candidate> findPageByPhoneNumber(@NotBlank String phoneNumber, Pageable pageable) {
        return findCandidatesPage(() -> candidatePagingRepository.findAllByPhoneNumber(phoneNumber, pageable),
                "phone number",
                phoneNumber
        );
    }

    public void deleteCandidate(@Valid Candidate candidate) {
        if (!candidateRepository.existsById(candidate.getId())) {
            log.warn("CandidateService: Attempt to delete non existing candidate");
            throw new IllegalArgumentException("Attempt to delete non existing candidate");
        }
        try {
            candidateRepository.delete(candidate);
            log.debug("CandidateService: Candidate with id {} was deleted", candidate.getId());
        } catch (DataIntegrityViolationException ex) {
            log.error("CandidateService: Error occurred due to candidate deletion with id {}", candidate.getId());
            throw new DataIntegrityConflictException("CandidateService: Error occurred due to candidate deletion", ex);
        }
    }


    public void addCandidate(@Valid Candidate candidate) {
        try {
            candidateRepository.save(candidate);
            log.debug("CandidateService: Candidate successfully saved");
        } catch (DataIntegrityViolationException ex) {
            log.error("CandidateService: Error occurred due to candidate saving");
            throw new DataIntegrityConflictException("CandidateService: Error occurred due to candidate saving", ex);
        }
    }

//    @CachePut
    public Page<Candidate> findAllCandidatesInProcess(Pageable pageable){
        Page<Candidate> byCompletedNot = candidatePagingRepository.findByCompletedNot(Completed.COMPLETED, pageable);
        if(byCompletedNot.getContent().isEmpty()){
            log.debug("CandidateService: No candidates found");
        } else {
            log.debug("CandidateService: Found {} candidates", byCompletedNot.getContent().size());
        }
        return byCompletedNot;
    }

    //TODO error handling
    public GetCandidatesResponseDto findBySearchQuery(String query, Pageable pageable) {
        CandidateFullSearchSpecification spec = new CandidateFullSearchSpecification(query);
        return new GetCandidatesResponseDto(candidatePagingRepository.findAll(spec, pageable), HttpStatus.OK);
    }
    // TODO error handling
    public Page<Candidate> getAllNotCompleted(Completed completed, Pageable pageable) {
        return candidatePagingRepository.findByCompletedNot(completed, pageable);
    }

    // TODO error handling
    public GetCandidatesResponseDto findAllCandidatesWithoutRecruiterAndEscortAndNotCompleted(Pageable pageable){
        CandidateWithoutCuratorAndEscortSpecification spec = new CandidateWithoutCuratorAndEscortSpecification();
        return new GetCandidatesResponseDto( candidatePagingRepository.findAll(spec, pageable), HttpStatus.OK);
    }
}
