package com.recruiting.center.crm.service.candidate;

import com.recruiting.center.crm.annotations.IT;
import com.recruiting.center.crm.database.IntegrationTestsDatabase;
import com.recruiting.center.crm.entity.candidate.MilitaryUnit;
import com.recruiting.center.crm.service.servicexceptions.MilitaryUnitNotFound;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@IT
@Transactional
class MilitaryUnitServiceTest extends IntegrationTestsDatabase {
    @Autowired
    private MilitaryUnitService militaryUnitService;

    @Test
    void findById() {
        assertNotNull(militaryUnitService.findById(100L));
    }

    @Test
    void notFoundById(){
        assertThrows(MilitaryUnitNotFound.class, () -> militaryUnitService.findById(99L));
    }

    @Test
    void findAllMilitaryUnits() {
        assertEquals(5, militaryUnitService.findAllMilitaryUnits().size());
    }

    @Test
    void findByUnitName() {
        assertNotNull(militaryUnitService.findByUnitName("А1234"));
    }


    @Test
    @Rollback
    void deleteMilitaryUnit() {
        militaryUnitService.saveMilitaryUnit(MilitaryUnit.builder()
                        .unitName("A7333")
                        .strictUnitName("A7332")
                .build());
        int beforeDeletion = militaryUnitService.findAllMilitaryUnits().size();
        militaryUnitService.deleteMilitaryUnit(militaryUnitService.findByUnitName("A7333"));
        int afterDeletion = militaryUnitService.findAllMilitaryUnits().size();

        assertEquals(beforeDeletion-1, afterDeletion);
    }

    @Test
    void deleteNonValidMilitaryUnit() {
        MilitaryUnit a1234 = militaryUnitService.findByUnitName("А1234");
        a1234.setId(123L);
        assertThrows(JpaSystemException.class, () ->
                militaryUnitService.deleteMilitaryUnit(
                        a1234
                ));
    }

    @Test
    @Rollback
    void saveMilitaryUnit() {
        int beforeAdding = militaryUnitService.findAllMilitaryUnits().size();
        militaryUnitService.saveMilitaryUnit(MilitaryUnit.builder()
                        .strictUnitName("A7221")
                        .unitName("A7221")
                .build());
        int afterAdding = militaryUnitService.findAllMilitaryUnits().size();
        assertEquals(beforeAdding+1, afterAdding);
    }

    @Test
    void saveNonValidMilitaryUnit() {
        assertThrows(ValidationException.class, () ->
                militaryUnitService.saveMilitaryUnit(MilitaryUnit.builder()
                                .strictUnitName("А7373")
                        .build()));
    }
}