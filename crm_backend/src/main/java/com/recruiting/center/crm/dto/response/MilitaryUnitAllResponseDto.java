package com.recruiting.center.crm.dto.response;

import com.recruiting.center.crm.entity.candidate.MilitaryUnit;
import org.springframework.http.HttpStatus;

import java.util.List;

public record MilitaryUnitAllResponseDto(List<MilitaryUnit> content, HttpStatus httpStatus) {

}
