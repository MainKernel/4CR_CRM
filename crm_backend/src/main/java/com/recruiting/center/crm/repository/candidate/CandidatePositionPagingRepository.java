package com.recruiting.center.crm.repository.candidate;

import com.recruiting.center.crm.entity.candidate.CandidatePosition;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatePositionPagingRepository extends PagingAndSortingRepository<CandidatePosition, Long> {
}
