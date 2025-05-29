package com.recruiting.center.crm.dto.response;

import com.recruiting.center.crm.entity.candidate.CandidateStatus;
import org.springframework.http.HttpStatus;

import java.util.List;

public record CandidateAllStatusesResponseDto (List<CandidateStatus> content, HttpStatus httpStatus){
}
