package com.recruiting.center.crm.service.appuser;

import com.recruiting.center.crm.entity.appuser.WorkUnit;
import com.recruiting.center.crm.repository.appuser.WorkUnitRepository;
import com.recruiting.center.crm.service.servicexceptions.DataIntegrityConflictException;
import com.recruiting.center.crm.service.servicexceptions.WorkUnitNotFoundException;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class WorkUnitService {
    private final WorkUnitRepository workUnitRepository;

    public WorkUnit findById(Long id){
        return workUnitRepository.findById(id).orElseThrow(() -> {
            log.warn("WorkUnitService: Work unit not found");
            return new WorkUnitNotFoundException("WorkUnitService: Work unit not found");
        });
    }

    public WorkUnit findByUnitName(String unitName){
        if(unitName == null || unitName.isBlank()){
            log.error("WorkUnitService: Attempt to search unit with empty unit name");
            throw new IllegalArgumentException("Attempt to search unit with empty unit name");
        }

        return workUnitRepository.findByUnitName(unitName).orElseThrow(() -> {
            log.warn("WorkUnitService: Unit with name {} not found", unitName);
            return new WorkUnitNotFoundException("Work unit not found");
        });
    }

    public List<WorkUnit> findAllWorkUnits(){
        List<WorkUnit> all = workUnitRepository.findAll();
        if(all.isEmpty()){
            log.debug("WorkUnitService: Not work units found");
        } else {
            log.debug("WorkUnitService: Found {} work units", all.size());
        }
        return all;
    }

    public void deleteById(Long id) {
        if (!workUnitRepository.existsById(id)) {
            log.warn("WorkUnit with ID {} not found for deletion", id);
            throw new WorkUnitNotFoundException("Work unit not found with id " + id);
        }

        try {
            workUnitRepository.deleteById(id);
            log.info("Successfully deleted WorkUnit with ID {}", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("Cannot delete WorkUnit with ID {} due to data integrity violation", id, ex);
            throw new DataIntegrityConflictException(
                    "WorkUnit is referenced by other entities. Deletion is prohibited.",
                    ex
            );
        }
    }

    public void saveWorkUnit(WorkUnit workUnit){
        if(workUnit == null){
            log.error("WorkUnitService: Attempt to save null work unit");
            throw new IllegalArgumentException("WorkUnitService: Attempt to save null work unit");
        }

        try {
            workUnitRepository.save(workUnit);
            log.debug("WorkUnitService: Saved work unit with name {}",workUnit.getUnitName());
        } catch (DataIntegrityViolationException ex){
            log.error("WorkUnitService: Error occurred due to saving");
            throw new DataIntegrityConflictException("WorkUnitService: Error occurred due to saving", ex);
        }
    }

}
