package com.recruiting.center.crm.service.controller;

import com.recruiting.center.crm.dto.response.CandidateAllStatusesResponseDto;
import com.recruiting.center.crm.service.candidate.CandidateStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandidateStatusControllerService {
    private final CandidateStatusService candidateStatusService;

    public CandidateAllStatusesResponseDto getAllStatuses() {
        return new CandidateAllStatusesResponseDto(candidateStatusService.findAllStatuses(), HttpStatus.OK);
    }
}
