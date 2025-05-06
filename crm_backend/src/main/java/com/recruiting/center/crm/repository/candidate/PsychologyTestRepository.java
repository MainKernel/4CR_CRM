package com.recruiting.center.crm.repository.candidate;

import com.recruiting.center.crm.entity.candidate.PsychologicalTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PsychologyTestRepository extends JpaRepository<PsychologicalTest, Long> {

    Optional<PsychologicalTest> findByStatus(String status);
}
