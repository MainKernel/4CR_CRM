package com.recruiting.center.crm.repository.candidate;

import com.recruiting.center.crm.entity.candidate.CandidatePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatePositionRepository extends JpaRepository<CandidatePosition, Long> {

}
