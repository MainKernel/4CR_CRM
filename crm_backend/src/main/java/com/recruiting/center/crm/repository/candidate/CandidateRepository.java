package com.recruiting.center.crm.repository.candidate;

import com.recruiting.center.crm.entity.candidate.Candidate;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Optional<Candidate> findCandidateById(Long id);
    List<Candidate> findCandidateByPhoneNumber(String phoneNumber);
    List<Candidate> findCandidateByRecruiter(String recruiter);
    List<Candidate> findCandidateByCurator(String curator);
    List<Candidate> findCandidateByEscortedBy(String escortedBy);
    List<Candidate> findCandidateByRecommendationLetter(LocalDate date);

}
