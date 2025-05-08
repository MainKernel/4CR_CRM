package com.recruiting.center.crm.service.appuser;

import com.recruiting.center.crm.annotations.IT;
import com.recruiting.center.crm.database.IntegrationTestsDatabase;
import com.recruiting.center.crm.entity.appuser.WorkUnit;
import com.recruiting.center.crm.service.servicexceptions.WorkUnitNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@IT
class WorkUnitServiceTest extends IntegrationTestsDatabase {
    @Autowired
    private WorkUnitService workUnitService;

    @Test
    void findById() {
        assertNotNull(workUnitService.findById(100L));
    }

    @Test
    void workUnitNotFoundById() {
        assertThrows(WorkUnitNotFoundException.class, () -> workUnitService.findById(999L));
    }

    @Test
    void findByUnitName() {
        WorkUnit findByUnitName = workUnitService.findByUnitName("Штаб");
        assertEquals(101, findByUnitName.getId());
    }

    @Test
    void notFoundByUnitName() {
        assertThrows(WorkUnitNotFoundException.class, () -> workUnitService.findByUnitName("Non existed work unit"));
    }

    @Test
    void findAllWorkUnits() {
        List<WorkUnit> allWorkUnits = workUnitService.findAllWorkUnits();
        assertEquals(5, allWorkUnits.size());
    }

    @Test
    void deleteById() {
        List<WorkUnit> beforeDeletion = workUnitService.findAllWorkUnits();

        workUnitService.deleteById(102L);

        List<WorkUnit> afterDeletion = workUnitService.findAllWorkUnits();

        assertEquals(beforeDeletion.size() - 1, afterDeletion.size());
    }

    @Test
    void saveWorkUnit() {
        List<WorkUnit> beforeSaving = workUnitService.findAllWorkUnits();
        WorkUnit workUnit = WorkUnit.builder().unitName("Test Unit").build();
        workUnitService.saveWorkUnit(workUnit);
        List<WorkUnit> afterSaving = workUnitService.findAllWorkUnits();

        assertEquals(beforeSaving.size() + 1, afterSaving.size());
    }
}