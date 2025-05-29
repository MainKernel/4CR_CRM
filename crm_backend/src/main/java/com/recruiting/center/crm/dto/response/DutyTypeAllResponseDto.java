package com.recruiting.center.crm.dto.response;

import com.recruiting.center.crm.entity.candidate.DutyType;
import org.springframework.http.HttpStatus;

import java.util.List;

public record DutyTypeAllResponseDto(List<DutyType> content, HttpStatus httpStatus) {
}
