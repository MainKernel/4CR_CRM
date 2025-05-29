package com.recruiting.center.crm.service.controller;

import com.recruiting.center.crm.dto.request.CandidateAddRequestDto;
import com.recruiting.center.crm.dto.response.AddCandidateResponseDto;
import com.recruiting.center.crm.dto.response.GetCandidatesResponseDto;
import com.recruiting.center.crm.entity.candidate.enums.Completed;
import com.recruiting.center.crm.service.candidate.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CandidatesControllerService {
    private final CandidateService cService;
    private final CandidateStatusService candidateStatusService;
    private final MilitaryUnitService militaryUnitService;
    private final PsychologyTestService psychologyTestService;
    private final DutyTypeService dutyTypeService;

    public GetCandidatesResponseDto getAllCandidates(Pageable pageable){
        log.debug("CandidateControllerService: Getting all candidates in process is 200 OK");
        return new GetCandidatesResponseDto(cService.findAllCandidatesInProcess(pageable), HttpStatus.OK);
    }

    public GetCandidatesResponseDto getAllNotCompletedCandidates(Pageable pageable) {
        return new GetCandidatesResponseDto(cService.getAllNotCompleted(Completed.COMPLETED, pageable), HttpStatus.OK);
    }

    public AddCandidateResponseDto addCandidate(CandidateAddRequestDto candidateRequestDto){
        try {
//            Candidate candidate = Candidate.builder()
//                    .name(candidateRequestDto.name())
//                    .surname(candidateRequestDto.surname())
//                    .middleName(candidateRequestDto.middleName())
//                    .phoneNumber(candidateRequestDto.phone())
//                    .status(candidateStatusService.findStatusByStatusName(candidateRequestDto.status()))
//                    .dutyType(dutyTypeService.findByStatus(candidateRequestDto.dutyType()))
//                    .militaryUnit(militaryUnitService.findByUnitName(candidateRequestDto.unit()))
//                    .recommendationLetter(candidateRequestDto.rlDate())
//                    .recruiter(candidateRequestDto.recruiter())
//                    .curator(candidateRequestDto.curator())
//                    .escortedBy(candidateRequestDto.escort())
//                    .psychologicalTest(psychologyTestService.findByStatus(candidateRequestDto.psychologicalTest()))
//                    .territoryCenterRecord(candidateRequestDto.territoryRecord())
//                    .enrolmentOrderDate(candidateRequestDto.unitOrder())
//                    .trainingCenterOrder(candidateRequestDto.order())
//                    .trainingCenterOrderNumber(candidateRequestDto.orderNumber())
//                    .build();

//            cService.addCandidate(candidate);
            return new AddCandidateResponseDto(HttpStatus.OK);
        } catch (Exception ex){
            return new AddCandidateResponseDto(HttpStatus.BAD_REQUEST);
        }
    }

    public GetCandidatesResponseDto getAllBySearchQuery(String query, Pageable pageable) {
        return cService.findBySearchQuery(query, pageable);
    }

    public GetCandidatesResponseDto getAllCandidatesWithoutRecruiterAndEscortAndNotCompleted(Pageable pageable){
        return cService.findAllCandidatesWithoutRecruiterAndEscortAndNotCompleted(pageable);
    }
}
