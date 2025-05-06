package com.recruiting.center.crm.repository.candidate;

import com.recruiting.center.crm.entity.candidate.DutyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DutyTypeRepository extends JpaRepository<DutyType, Long> {
    Optional<DutyType> findByStatus(String status);
}
