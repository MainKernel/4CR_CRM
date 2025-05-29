package com.recruiting.center.crm.controller;

import com.recruiting.center.crm.dto.response.MilitaryUnitAllResponseDto;
import com.recruiting.center.crm.service.controller.MilitaryUnitControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/military")
@RequiredArgsConstructor
public class MilitaryUnitController {
    private final MilitaryUnitControllerService militaryUnitControllerService;


    @GetMapping
    private MilitaryUnitAllResponseDto getAllUnits() {
        return militaryUnitControllerService.getAllMilitaryUnits();
    }
}
