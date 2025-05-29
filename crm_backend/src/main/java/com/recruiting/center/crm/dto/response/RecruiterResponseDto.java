package com.recruiting.center.crm.dto.response;

import com.recruiting.center.crm.dto.entity.RecruiterDto;
import org.springframework.http.HttpStatus;

import java.util.List;

public record RecruiterResponseDto(List<RecruiterDto> content, HttpStatus httpStatus) {

}
