package com.recruiting.center.crm.controller;

import com.recruiting.center.crm.dto.response.RecruiterResponseDto;
import com.recruiting.center.crm.dto.response.WorkUnitsResponseDto;
import com.recruiting.center.crm.service.controller.AppUserControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/app-user")
public class AppUserController {
    private final AppUserControllerService appUserControllerService;

    @GetMapping("/recruiters")
    public RecruiterResponseDto getAllRecruiters(){
        return appUserControllerService.getAllRecruiters();
    }

    @GetMapping("/work-units")
    public WorkUnitsResponseDto getAllWorkUnits() {
        return appUserControllerService.getAllWorkUnits();
    }
}
