package com.recruiting.center.crm.controller;

import com.recruiting.center.crm.dto.response.CandidateAllStatusesResponseDto;
import com.recruiting.center.crm.service.controller.CandidateStatusControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")
@RequiredArgsConstructor
public class CandidateStatusController {
    private final CandidateStatusControllerService candidateStatusControllerService;

    @GetMapping
    public CandidateAllStatusesResponseDto getAllStatuses() {
        return candidateStatusControllerService.getAllStatuses();
    }
}
