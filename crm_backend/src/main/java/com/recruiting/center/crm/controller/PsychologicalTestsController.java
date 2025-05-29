package com.recruiting.center.crm.controller;

import com.recruiting.center.crm.dto.response.PsychologyTestsAllResponseDto;
import com.recruiting.center.crm.service.controller.PsychologicalTestsControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/psychological")
@RequiredArgsConstructor
public class PsychologicalTestsController {
    private final PsychologicalTestsControllerService psychologicalTestsControllerService;

    @GetMapping
    public PsychologyTestsAllResponseDto getAllPsychologicalTestsStatuses() {
        return psychologicalTestsControllerService.getAllPsychologicalTests();
    }

}
