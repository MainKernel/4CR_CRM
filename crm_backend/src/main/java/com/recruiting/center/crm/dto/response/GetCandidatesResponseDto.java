package com.recruiting.center.crm.dto.response;

import com.recruiting.center.crm.entity.candidate.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

public record GetCandidatesResponseDto(Page<Candidate> content, HttpStatus httpStatus) {
}
