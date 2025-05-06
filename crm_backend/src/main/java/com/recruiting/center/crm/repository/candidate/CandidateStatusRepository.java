package com.recruiting.center.crm.repository.candidate;

import com.recruiting.center.crm.entity.candidate.CandidateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateStatusRepository extends JpaRepository<CandidateStatus,Long > {

}
