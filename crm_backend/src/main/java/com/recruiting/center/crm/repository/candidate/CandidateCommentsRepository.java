package com.recruiting.center.crm.repository.candidate;

import com.recruiting.center.crm.entity.candidate.CandidateComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CandidateCommentsRepository extends JpaRepository<CandidateComment,Long> {

    List<CandidateComment> findByCandidateId(Long id);

}
