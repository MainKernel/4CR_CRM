package com.recruiting.center.crm.service.controller;

import com.recruiting.center.crm.dto.response.DutyTypeAllResponseDto;
import com.recruiting.center.crm.service.candidate.DutyTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DutyTypeControllerService {
    private final DutyTypeService dutyTypeService;

    public DutyTypeAllResponseDto getAllDutyTypes(){
        return new DutyTypeAllResponseDto(dutyTypeService.findAll(), HttpStatus.OK);
    }
}
