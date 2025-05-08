package com.recruiting.center.crm.service.appuser;

import com.recruiting.center.crm.entity.appuser.UserPosition;
import com.recruiting.center.crm.repository.appuser.UserPositionRepository;
import com.recruiting.center.crm.service.servicexceptions.DataIntegrityConflictException;
import com.recruiting.center.crm.service.servicexceptions.UserPositionNotFound;
import com.recruiting.center.crm.service.servicexceptions.UserPositionNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserPositionService {
    private final UserPositionRepository userPositionRepository;

    public UserPosition findById(Long id) {
        return userPositionRepository.findById(id).orElseThrow(() -> {
            log.warn("User position with id {} not wound", id);
            return new UserPositionNotFoundException("User position not found");
        });
    }

    public UserPosition findByUnitName(@NotBlank String unitName) {
        return userPositionRepository.findByUnitPosition(unitName).orElseThrow(() -> {
            log.error("UserPositionService: No user position with name {} found", unitName);
            return new UserPositionNotFound("UserPositionService: no user position found with name" + unitName);
        });
    }

    public void save(@Valid UserPosition userPosition) {
        try {
            userPositionRepository.save(userPosition);
            log.debug("UserPositionService: user position successfully saved");
        } catch (DataIntegrityViolationException ex) {
            log.error("UserPositionService: Error occurred due to the saving");
            throw new DataIntegrityConflictException("UserPositionService: error occurred due to the saving", ex);
        }
    }

    public void delete(@Valid UserPosition userPosition) {
        if (!userPositionRepository.existsById(userPosition.getId())) {
            log.error("UserPositionService: Attempt delete non existing position");
            throw new IllegalArgumentException("UserPositionService: Attempt delete non existing position");
        }

        try {
            userPositionRepository.delete(userPosition);
            log.debug("UserPositionService: user position with id {} successfully deleted", userPosition.getId());
        } catch (DataIntegrityViolationException ex) {
            log.error("UserPositionService: error occurred due to position deletion");
            throw new DataIntegrityConflictException("UserPositionService: error occurred due to position deletion", ex);
        }
    }

    public List<UserPosition> findAll(){
        List<UserPosition> all = userPositionRepository.findAll();

        if(all.isEmpty()){
            log.debug("UserPositionService: No user positions found");
        } else {
            log.debug("UserPositionService: Found {} user positions", all.size());
        }

        return all;
    }

}
