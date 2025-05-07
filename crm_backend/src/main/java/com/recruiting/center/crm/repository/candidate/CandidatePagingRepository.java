package com.recruiting.center.crm.repository.candidate;

import com.recruiting.center.crm.entity.candidate.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CandidatePagingRepository extends PagingAndSortingRepository<Candidate, Long> {

    Page<Candidate> findAllByRecruiter(String recruiter, Pageable pageable);
    Page<Candidate> findAllByCurator(String curator, Pageable pageable);
    Page<Candidate> findAllByRecommendationLetter(LocalDate localDate, Pageable pageable);
    Page<Candidate> findAllByEscortedBy(String escortedBy, Pageable pageable);
    Page<Candidate> findAllByPhoneNumber(String phoneNumber, Pageable pageable);
}
