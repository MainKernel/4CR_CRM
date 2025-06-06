package com.recruiting.center.crm.service.appuser;

import com.recruiting.center.crm.annotations.IT;
import com.recruiting.center.crm.database.IntegrationTestsDatabase;
import com.recruiting.center.crm.entity.appuser.UserPosition;
import com.recruiting.center.crm.service.servicexceptions.DocumentNotFoundException;
import com.recruiting.center.crm.service.servicexceptions.UserNotFoundException;
import com.recruiting.center.crm.service.servicexceptions.UserPositionNotFound;
import com.recruiting.center.crm.service.servicexceptions.UserPositionNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@IT
@Transactional
class UserPositionServiceTest extends IntegrationTestsDatabase {

    @Autowired
    private UserPositionService userPositionService;

    @Test
    void findById() {
        UserPosition byId = userPositionService.findById(100L);
        assertNotNull(byId);
    }

    @Test
    void findByNonExistingId(){
        assertThrows(UserPositionNotFoundException.class, () -> userPositionService.findById(99L));
    }

    @Test
    void findByUnitName() {
        UserPosition findByUnitName = userPositionService.findByUnitName("Рекрутер");
        assertNotNull(findByUnitName);
    }

    @Test
    void findNonExistingPosition(){
        assertThrows(UserPositionNotFound.class, () -> userPositionService.findByUnitName("Non existing position"));
    }

    @Test
    @Rollback
    void save() {
        List<UserPosition> beforeSaving = userPositionService.findAll();
        userPositionService.save(UserPosition.builder()
                .unitPosition("Аналітик")
                .build());
        List<UserPosition> afterSaving = userPositionService.findAll();
        assertEquals(beforeSaving.size() + 1, afterSaving.size());
    }

    @Test
    void saveNonValidPosition(){
        UserPosition byId = userPositionService.findById(100L);
        byId.setUnitPosition(null);

        assertThrows(ValidationException.class, () -> userPositionService.save(byId));
    }

    @Test
    @Rollback
    void delete() {
        List<UserPosition> beforeDeleting = userPositionService.findAll();
        userPositionService.delete(userPositionService.findById(101L));
        List<UserPosition> afterDeleting = userPositionService.findAll();
        assertEquals(afterDeleting.size(), beforeDeleting.size() - 1 );
    }

    @Test
    void deleteNonExistingPosition(){
        UserPosition byId = userPositionService.findById(100L);
        byId.setId(300L);

        assertThrows(JpaSystemException.class, () -> userPositionService.delete(byId));
    }
}