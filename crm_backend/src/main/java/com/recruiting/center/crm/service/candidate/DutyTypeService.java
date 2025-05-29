package com.recruiting.center.crm.service.candidate;

import com.recruiting.center.crm.entity.candidate.DutyType;
import com.recruiting.center.crm.repository.candidate.DutyTypeRepository;
import com.recruiting.center.crm.service.servicexceptions.DataIntegrityConflictException;
import com.recruiting.center.crm.service.servicexceptions.DutyTypeNotFound;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@Validated
public class DutyTypeService {
    private final DutyTypeRepository dutyTypeRepository;

    public List<DutyType> findAll() {
        List<DutyType> all = dutyTypeRepository.findAll();

        if (all.isEmpty()) {
            log.debug("DutyTypeService: No Duty type statuses found");
        } else {
            log.debug("DutyTypeService: Found {} statuses", all.size());
        }
        return all;
    }

    public DutyType findDutyTypeById(Long id) {
        return dutyTypeRepository.findById(id).orElseThrow(() -> {
            log.error("DutyTypeService: No duty type with id {} found", id);
            return new DutyTypeNotFound("DutyTypeService: No duty type found with id " + id);
        });
    }

    public void deleteDutyType(@Valid DutyType dutyType) {
        if (!dutyTypeRepository.existsById(dutyType.getId())) {
            log.error("DutyTypeService: No duty type found");
            throw new IllegalArgumentException("No duty type found");
        }

        try {
            dutyTypeRepository.delete(dutyType);
            log.debug("DutyTypeService: successfully deleted {}", dutyType);
        } catch (DataIntegrityViolationException ex) {
            log.error("DutyTypeService: error occurred due to deletion of duty type");
            throw new DataIntegrityConflictException("DutyTypeService: error occurred due to deletion of duty type", ex);
        }
    }

    public void addDutyType(@Valid DutyType dutyType) {
        try{
            dutyTypeRepository.save(dutyType);
            log.debug("DutyTypeService: duty type successfully saved");
        } catch (DataIntegrityViolationException ex){
            log.error("DutyTypeService: Error due to saving");
            throw new DataIntegrityConflictException("DutyTypeService: Error due to saving", ex);
        }
    }

    public DutyType findByStatus(String status) {
        return dutyTypeRepository.findByStatus(status).orElseThrow(() -> {
            log.error("DutyTypeService: Duty type was not found");
            return new DutyTypeNotFound("DutyTypeService: Duty type was not found");
        });
    }
}
