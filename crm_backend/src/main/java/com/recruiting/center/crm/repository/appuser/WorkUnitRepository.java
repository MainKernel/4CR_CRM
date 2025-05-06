package com.recruiting.center.crm.repository.appuser;

import com.recruiting.center.crm.entity.appuser.WorkUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkUnitRepository extends JpaRepository<WorkUnit, Long> {
    Optional<WorkUnit> findByUnitName(String unitName);
}
