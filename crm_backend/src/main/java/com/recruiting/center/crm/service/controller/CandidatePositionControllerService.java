package com.recruiting.center.crm.service.controller;

import com.recruiting.center.crm.dto.response.CandidateAllPositionsResponseDto;
import com.recruiting.center.crm.dto.response.CandidateAllStatusesResponseDto;
import com.recruiting.center.crm.service.candidate.CandidatePositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandidatePositionControllerService {
    private final CandidatePositionService candidatePositionService;


    public CandidateAllPositionsResponseDto getAllPositions() {
        return new CandidateAllPositionsResponseDto(candidatePositionService.findAll(), HttpStatus.OK);
    }
}
