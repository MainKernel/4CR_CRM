package com.recruiting.center.crm.controller;

import com.recruiting.center.crm.dto.response.DutyTypeAllResponseDto;
import com.recruiting.center.crm.service.controller.DutyTypeControllerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/duty")
@RequiredArgsConstructor
@Slf4j
public class DutyTypeController {
    private final DutyTypeControllerService dutyTypeControllerService;

    @GetMapping
    public DutyTypeAllResponseDto getDutyTypes() {
        return dutyTypeControllerService.getAllDutyTypes();
    }

}
