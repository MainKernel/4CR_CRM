package com.recruiting.center.crm.service.controller;

import com.recruiting.center.crm.dto.response.PsychologyTestsAllResponseDto;
import com.recruiting.center.crm.service.candidate.PsychologyTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PsychologicalTestsControllerService {
    private final PsychologyTestService psychologyTestService;

    public PsychologyTestsAllResponseDto getAllPsychologicalTests() {
        return new PsychologyTestsAllResponseDto(psychologyTestService.findAllPsychologicalTestStatuses(), HttpStatus.OK);
    }
}
