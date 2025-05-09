package com.recruiting.center.crm.service.appuser;

import com.recruiting.center.crm.annotations.IT;
import com.recruiting.center.crm.database.IntegrationTestsDatabase;
import com.recruiting.center.crm.entity.appuser.AppUser;
import com.recruiting.center.crm.entity.appuser.WorkUnit;
import com.recruiting.center.crm.service.servicexceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@IT
@Transactional
class AppUserServiceTest extends IntegrationTestsDatabase {
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private WorkUnitService workUnitService;
    @Autowired
    private UserPositionService userPositionService;

    @Test
    void getUserByUsername() {
        AppUser recruiter = appUserService.getUserByUsername("recruiter");

        assertNotNull(recruiter);
    }

    @Test
    void userNotFoundByUsername(){
        assertThrows(UserNotFoundException.class, () -> appUserService.getUserByUsername("Non existing username"));
    }

    @Test
    @Rollback
    void save() {
        AppUser appUser = AppUser.builder()
                .firstName("Test1")
                .secondName("Test 2")
                .workUnit(workUnitService.findByUnitName("Штаб"))
                .userPosition(userPositionService.findById(103L))
                .username("Test user")
                .password("$2a$10$dXjufsohfosdhfoshdofshof")
                .build();
        appUserService.save(appUser);
        AppUser testUser = appUserService.getUserByUsername("Test user");
        assertNotNull(testUser);
    }

    @Test
    void failToSaveUserWithConstraintViolation(){
        AppUser appUser = AppUser.builder()
                .firstName("Test1")
                .secondName("Test 2")
                .userPosition(userPositionService.findById(103L))
                .password("$2a$10$dXjufsohfosdhfoshdofshof")
                .build();

        assertThrows(ConstraintViolationException.class, () -> appUserService.save(appUser));
    }

    @Test
    void findByFirstName() {
        List<AppUser> byFirstName = appUserService.findByFirstName("Олександр");
        assertEquals(1, byFirstName.size());
    }

    @Test
    void notFoundByFirstName(){
        List<AppUser> byFirstName = appUserService.findByFirstName("Non existing first name");
        assertEquals(0, byFirstName.size());
    }

    @Test
    void findBySecondName() {
        List<AppUser> bySecondName = appUserService.findBySecondName("Іванова");
        assertEquals(1, bySecondName.size());
    }

    @Test
    void notFoundBySecondName(){
        List<AppUser> bySecondName = appUserService.findBySecondName("Non existing second name");
        assertEquals(0, bySecondName.size());
    }

    @Test
    void findByWorkUnit() {
        WorkUnit workUnit = workUnitService.findByUnitName("Відділ кадрів");
        List<AppUser> byWorkUnit = appUserService.findByWorkUnit(workUnit);
        assertEquals(1, byWorkUnit.size());
    }

    @Test
    void notFoundByWorkUnit(){
        List<AppUser> byWorkUnit = appUserService.findByWorkUnit(workUnitService.findById(104L));
        assertEquals(0, byWorkUnit.size());
    }

}