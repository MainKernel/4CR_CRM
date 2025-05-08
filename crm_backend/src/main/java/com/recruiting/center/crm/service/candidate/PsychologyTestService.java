package com.recruiting.center.crm.service.candidate;

import com.recruiting.center.crm.entity.candidate.PsychologicalTest;
import com.recruiting.center.crm.repository.candidate.PsychologyTestRepository;
import com.recruiting.center.crm.service.servicexceptions.DataIntegrityConflictException;
import com.recruiting.center.crm.service.servicexceptions.PsychologicalTestNotFound;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@Valid
public class PsychologyTestService {
    private final PsychologyTestRepository psychologyTestRepository;

    public PsychologicalTest findByStatus(@NotBlank String status) {
        return psychologyTestRepository.findByStatus(status).orElseThrow(
                () -> {
                    log.error("PsychologyTestService: Error occurred due to finding status {} ", status);
                    return new PsychologicalTestNotFound("MilitaryUnitService: Error occurred due to finding status");
                }
        );
    }

    public PsychologicalTest findTestStatusById(Long id){
        return psychologyTestRepository.findById(id).orElseThrow(() -> {
            log.error("PsychologyTestService: Error occurred due to finding by id");
            return new PsychologicalTestNotFound("PsychologyTestService: Error occurred due to finding test");
        });
    }

    public List<PsychologicalTest> findAllPsychologicalTestStatuses(){
        List<PsychologicalTest> all = psychologyTestRepository.findAll();
        if(all.isEmpty()){
            log.debug("PsychologyTestService: No test statuses found");
        }else {
            log.debug("PsychologyTestService: Found {} statuses", all.size());
        }
        return all;
    }

    public void deleteTestStatus(@Valid PsychologicalTest psychologicalTest){
        if(!psychologyTestRepository.existsById(psychologicalTest.getId())){
            log.error("PsychologyTestService: Status not found in database");
            throw new IllegalArgumentException("PsychologyTestService: Attempt delete non existing test status");
        }
        try{
            psychologyTestRepository.delete(psychologicalTest);
            log.debug("PsychologyTestService: Successfully delete test status");
        } catch (DataIntegrityViolationException ex){
            log.error("PsychologyTestService: Error occurred due to test deletion");
            throw new DataIntegrityConflictException("PsychologyTestService: Error occurred due to deletion", ex);
        }
    }

    public void saveTestStatus(@Valid PsychologicalTest psychologicalTest){
        try {
            psychologyTestRepository.save(psychologicalTest);
            log.debug("PsychologyTestService: Successfully saved test status");
        } catch (DataIntegrityViolationException ex){
            log.error("PsychologyTestService: Error occurred due to test status saving");
            throw new DataIntegrityConflictException("PsychologyTestService: Error occurred due to status saving", ex);
        }
    }
}
