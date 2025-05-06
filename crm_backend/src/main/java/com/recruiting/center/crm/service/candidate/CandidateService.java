package com.recruiting.center.crm.service.candidate;

import com.recruiting.center.crm.entity.candidate.Candidate;
import com.recruiting.center.crm.repository.candidate.CandidatePagingRepository;
import com.recruiting.center.crm.repository.candidate.CandidateRepository;
import com.recruiting.center.crm.service.servicexceptions.CandidateNotFoundException;
import com.recruiting.center.crm.utils.phone.PhoneValidationService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final CandidatePagingRepository candidatePagingRepository;
    private final PhoneValidationService phoneValidationService;

    /** Find candidates by specified role
     * @param repositoryMethod repository method to execute
     * @param role human-readable role name for logging
     * @param roleName actual value to search for
     * @return unmodifiable list of candidates or empty list if candidates not found
     */
    private List<Candidate> findCandidates(Supplier<List<Candidate>> repositoryMethod, String role, String roleName) {
        List<Candidate> candidates = repositoryMethod.get();

        if (candidates.isEmpty()) {
            log.warn("Candidates with {} {} not found", role, roleName);
        } else {
            log.debug("Found {} candidates with {} {}", candidates.size(), role, roleName);
        }

        return Collections.unmodifiableList(candidates);
    }

    private Page<Candidate> findCandidatesPage(Supplier<Page<Candidate>> pagingRepositoryMethod, String role, String roleName){
        Page<Candidate> candidates = pagingRepositoryMethod.get();

        if(candidates.isEmpty()){
            log.warn("Candidates page with {} {} not found", role, roleName);
        } else {
            log.debug("Candidates page found {} with {} {}", candidates.getTotalElements(),role, roleName);
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

    public List<Candidate> findByCurator(String curator) {
        return findCandidates(
                () -> candidateRepository.findCandidateByCurator(curator),
                "curator",
                curator
        );
    }

    public List<Candidate> findByEscortedBy(@NotBlank String escortedBy) {
        return findCandidates(
                () -> candidateRepository.findCandidateByEscortedBy(escortedBy),
                "escortedBy",
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

    public Page<Candidate> findPageByRecruiterId(Long id, Pageable pageable){
        return candidatePagingRepository.findAllByRecruiterId(id, pageable);
    }
}
