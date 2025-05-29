package com.recruiting.center.crm.dto.response;

import com.recruiting.center.crm.entity.candidate.CandidatePosition;
import org.springframework.http.HttpStatus;

import java.net.http.HttpResponse;
import java.util.List;

public record CandidateAllPositionsResponseDto(List<CandidatePosition> content, HttpStatus status) {
}
