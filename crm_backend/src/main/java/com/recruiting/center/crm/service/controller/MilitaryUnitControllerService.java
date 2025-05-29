package com.recruiting.center.crm.service.controller;

import com.recruiting.center.crm.dto.response.MilitaryUnitAllResponseDto;
import com.recruiting.center.crm.service.candidate.MilitaryUnitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MilitaryUnitControllerService {
    private final MilitaryUnitService militaryUnitService;

    public MilitaryUnitAllResponseDto getAllMilitaryUnits() {
        return new MilitaryUnitAllResponseDto(militaryUnitService.findAllMilitaryUnits(), HttpStatus.OK);
    }
}
