package com.recruiting.center.crm.service.candidate;

import com.recruiting.center.crm.entity.candidate.MilitaryUnit;
import com.recruiting.center.crm.repository.candidate.MilitaryUnitRepository;
import com.recruiting.center.crm.service.servicexceptions.DataIntegrityConflictException;
import com.recruiting.center.crm.service.servicexceptions.MilitaryUnitNotFound;
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
@Transactional
@Slf4j
@Validated
public class MilitaryUnitService {
    private final MilitaryUnitRepository militaryUnitRepository;

    public MilitaryUnit findById(Long id){
        return militaryUnitRepository.findById(id).orElseThrow(() -> {
            log.error("MilitaryUnitService: Error occurred due to finding military unit with id {}", id);
            return new MilitaryUnitNotFound("MilitaryUnitService: Error occurred due to finding military unit with id " + id);
        });
    }

    public List<MilitaryUnit> findAllMilitaryUnits(){
        List<MilitaryUnit> all = militaryUnitRepository.findAll();

        if(all.isEmpty()){
            log.debug("MilitaryUnitService: No military units found in database");
        } else {
            log.debug("MilitaryUnitService: Found {} military units", all.size());
        }
        return all;
    }

    //TODO map all "A" values from ukrainian to ENG

    public MilitaryUnit findByUnitName(String unitName) {
        if(unitName.isBlank()){
            log.error("MilitaryUnitService: Attempt to search with empty unit name");
            throw new IllegalArgumentException("MilitaryUnitService: Attempt to search with empty unit name");
        }
        return militaryUnitRepository.findByUnitName(unitName).orElseThrow(() -> {
            log.error("MilitaryUnitService: No unit with unit name {} found", unitName);
            return new MilitaryUnitNotFound("MilitaryUnitService: No unit found with unit name " + unitName);
        });
    }

    public void deleteMilitaryUnit(@Valid MilitaryUnit militaryUnit){
        if(!militaryUnitRepository.existsById(militaryUnit.getId())){
            log.error("MilitaryUnitService: Attempt to delete non existing unit");
            throw new IllegalArgumentException("MilitaryUnitService: Attempt to delete non existing unit");
        }

        try{
            militaryUnitRepository.delete(militaryUnit);
            log.debug("MilitaryUnitService: Successfully deleted");
        } catch (DataIntegrityViolationException ex){
            log.error("MilitaryUnitService: Error occurred due to deletion");
            throw new DataIntegrityConflictException("MilitaryUnitService: Error occurred due to deletion", ex);
        }
    }

    //TODO map all "A" values from ukrainian to ENG
    public void saveMilitaryUnit(@Valid MilitaryUnit militaryUnit){
        try{
            militaryUnitRepository.save(militaryUnit);
            log.debug("MilitaryUnitService: military unit successfully saved");
        } catch (DataIntegrityViolationException ex){
            log.error("MilitaryUnitService: Error occurred due to military unit saving");
            throw new DataIntegrityConflictException("MilitaryUnitService: Error ocurred due to saving", ex);
        }
    }
}
