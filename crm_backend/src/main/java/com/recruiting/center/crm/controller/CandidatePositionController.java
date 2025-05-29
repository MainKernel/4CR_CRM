package com.recruiting.center.crm.controller;

import com.recruiting.center.crm.dto.response.CandidateAllPositionsResponseDto;
import com.recruiting.center.crm.service.controller.CandidatePositionControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/position")
public class CandidatePositionController {
    private final CandidatePositionControllerService candidatePositionControllerService;

    @GetMapping
    public CandidateAllPositionsResponseDto getAllPositions() {
        return candidatePositionControllerService.getAllPositions();
    }

}
