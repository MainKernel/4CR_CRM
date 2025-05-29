package com.recruiting.center.crm.service.appuser;

import com.recruiting.center.crm.entity.appuser.AppUser;
import com.recruiting.center.crm.entity.appuser.WorkUnit;
import com.recruiting.center.crm.repository.appuser.AppUserRepository;
import com.recruiting.center.crm.repository.appuser.WorkUnitRepository;
import com.recruiting.center.crm.service.servicexceptions.DuplicatedUserException;
import com.recruiting.center.crm.service.servicexceptions.UserNotFoundException;
import com.recruiting.center.crm.service.servicexceptions.WorkUnitNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
@Transactional
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final WorkUnitRepository workUnitRepository;

    public AppUser getUserByUsername(String username) {
        if (username == null || username.isBlank()) {
            log.error("AppUserService: Attempted to search user with empty username");
            throw new IllegalArgumentException("Username is required");
        }
        return appUserRepository.findByUsername(username).orElseThrow(() -> {
                    log.warn("User not found {}", username);
                    return new UserNotFoundException("User not found");
                }
        );
    }

    public List<AppUser> getAllAppUsers() {
        return appUserRepository.findAll();
    }

    public void save(@Valid AppUser user) {
        if (user == null) {
            log.error("Attempt to save null user");
            throw new IllegalArgumentException("User cannot be null");
        }
        try {
            appUserRepository.save(user);
            log.info("User saved successfully: {}", user.getUsername());
        } catch (DataIntegrityViolationException ex) {
            log.error("Failed to save user (duplicated/invalid data): {}", user.getUsername(), ex);
            throw new DuplicatedUserException("User already exists", ex);
        }
    }

    public List<AppUser> findByFirstName(@NotBlank String firstName) {
        log.debug("AppUserService: search user by first name {}", firstName);

        List<AppUser> users = appUserRepository.findByFirstName(firstName);
        if (users.isEmpty()) {
            log.warn("AppUserService: No users found with first name {}", firstName);
        } else {
            log.info("Found {} users with first name: {}", users.size(), firstName);
        }
        return users;
    }

    public List<AppUser> findBySecondName(@NotBlank String secondName) {
        log.debug("AppService: search user by second name {}", secondName);

        List<AppUser> users = appUserRepository.findBySecondName(secondName);

        if (users.isEmpty()) {
            log.warn("AppUserService: No users found with second name {}", secondName);
        } else {
            log.info("Found {} users with second name: {}", users.size(), secondName);
        }

        return users;
    }

    public List<AppUser> findByWorkUnit(@Valid WorkUnit workUnit) {
        log.debug("AppService: search user by work unit {}", workUnit.getUnitName());

        if (workUnit.getId() == 0 || !workUnitRepository.existsById(workUnit.getId())) {
            log.error("AppService: Attempt find using non existing work unit");
            throw new WorkUnitNotFoundException("AppService: Attempt find using non existing work unit");
        }
        List<AppUser> users = appUserRepository.findByWorkUnit(workUnit);
        if (users.isEmpty()) {
            log.warn("AppUserService: No users found with work unit {}", workUnit.getUnitName());
        } else {
            log.info("Found {} users with work unit: {}", users.size(), workUnit.getUnitName());
        }

        return users;
    }

    public boolean isExist(String username) {
        return appUserRepository.findByUsername(username).isPresent();
    }

}
