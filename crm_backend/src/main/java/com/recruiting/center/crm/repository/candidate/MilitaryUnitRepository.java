package com.recruiting.center.crm.repository.candidate;

import com.recruiting.center.crm.entity.candidate.MilitaryUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MilitaryUnitRepository extends JpaRepository<MilitaryUnit, Long> {

    Optional<MilitaryUnit> findByUnitName(String unitName);
}
