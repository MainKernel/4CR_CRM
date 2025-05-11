package com.recruiting.center.crm.service.candidate;

import com.recruiting.center.crm.annotations.IT;
import com.recruiting.center.crm.database.IntegrationTestsDatabase;
import com.recruiting.center.crm.entity.candidate.DutyType;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@IT
class DutyTypeServiceTest extends IntegrationTestsDatabase {
    @Autowired
    private DutyTypeService dutyTypeService;

    @Test
    void findAll() {
        assertEquals(3, dutyTypeService.findAll().size());
    }

    @Test
    void findDutyTypeById() {
        DutyType dutyTypeById = dutyTypeService.findDutyTypeById(100L);
        assertEquals("Контракт", dutyTypeById.getStatus());
    }

    @Test
    @Rollback
    void deleteDutyType() {
        dutyTypeService.addDutyType(DutyType.builder()
                .color("FFFFFF")
                .status("Test")
                .build());
        int beforeDeletion = dutyTypeService.findAll().size();
        dutyTypeService.deleteDutyType(dutyTypeService.findDutyTypeById(103L));
        int afterDeletion = dutyTypeService.findAll().size();
        assertEquals(beforeDeletion - 1, afterDeletion);
    }

    @Test
    void deleteNonValidDutyType() {
        assertThrows(ValidationException.class,
                () -> dutyTypeService
                        .deleteDutyType(DutyType
                                .builder()
                                .status("Test")
                                .build()));

    }

    @Test
    @Rollback
    void addDutyType() {
        int beforeAdding = dutyTypeService.findAll().size();
        dutyTypeService.addDutyType(DutyType.builder()
                .status("Test status")
                .color("F20000")
                .build());
        int afterAddition = dutyTypeService.findAll().size();
        assertEquals(beforeAdding + 1, afterAddition);
    }

    @Test
    void addNonValidDutyType() {
        assertThrows(ValidationException.class,
                () -> dutyTypeService
                        .addDutyType(DutyType
                                .builder()
                                .color("F20000")
                                .build()));
    }
}