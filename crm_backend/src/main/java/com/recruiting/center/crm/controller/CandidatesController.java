package com.recruiting.center.crm.controller;

import com.recruiting.center.crm.dto.request.CandidateAddRequestDto;
import com.recruiting.center.crm.dto.response.AddCandidateResponseDto;
import com.recruiting.center.crm.dto.response.GetCandidatesResponseDto;
import com.recruiting.center.crm.service.controller.CandidatesControllerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
@Slf4j
public class CandidatesController {
    private final CandidatesControllerService candidatesControllerService;

    @GetMapping
    public GetCandidatesResponseDto getCandidatesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size
    ) {
        return candidatesControllerService
                .getAllCandidates(PageRequest.of(page, size));
    }
    @GetMapping("/candidate-page")
    public GetCandidatesResponseDto getCandidatesForCandidatePage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size
    ){
        return candidatesControllerService.getAllCandidatesWithoutRecruiterAndEscortAndNotCompleted(PageRequest.of(page, size));
    }
    @GetMapping("/not-completed")
    public GetCandidatesResponseDto getNotCompleted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size
    ){
        return candidatesControllerService.getAllCandidates(PageRequest.of(page, size));
    }

    @GetMapping("/candidate-page/search")
    public GetCandidatesResponseDto getCandidatesBy(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam String search
    ){
       return candidatesControllerService.getAllBySearchQuery(search, PageRequest.of(page, size));
    }

    @PostMapping("/add")
    public AddCandidateResponseDto addCandidate(@RequestBody CandidateAddRequestDto candidateAddRequestDto) {
        System.out.println(candidateAddRequestDto.toString());
        return candidatesControllerService.addCandidate(candidateAddRequestDto);
    }


}
