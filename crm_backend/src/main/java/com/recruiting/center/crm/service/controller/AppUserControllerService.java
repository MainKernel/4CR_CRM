package com.recruiting.center.crm.service.controller;

import com.recruiting.center.crm.dto.entity.RecruiterDto;
import com.recruiting.center.crm.dto.response.RecruiterResponseDto;
import com.recruiting.center.crm.dto.response.WorkUnitsResponseDto;
import com.recruiting.center.crm.service.appuser.AppUserService;
import com.recruiting.center.crm.service.appuser.WorkUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserControllerService {
    private final AppUserService appUserService;
    private final WorkUnitService workUnitService;

    public RecruiterResponseDto getAllRecruiters() {
        List<RecruiterDto> collect = appUserService.getAllAppUsers().stream()
                .map(f -> new RecruiterDto(f.getFirstName() + " " + f.getSecondName())).toList();
        return new RecruiterResponseDto(collect, HttpStatus.OK);
    }

    public WorkUnitsResponseDto getAllWorkUnits() {
        return new WorkUnitsResponseDto(
                workUnitService.findAllWorkUnits(), HttpStatus.OK
        );
    }
}
