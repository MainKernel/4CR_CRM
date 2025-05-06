package com.recruiting.center.crm.service.appuser;

import com.recruiting.center.crm.entity.appuser.UserPosition;
import com.recruiting.center.crm.repository.appuser.UserPositionRepository;
import com.recruiting.center.crm.service.servicexceptions.UserPositionNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

}
