package com.recruiting.center.crm.dto.response;

import com.recruiting.center.crm.entity.candidate.PsychologicalTest;
import org.springframework.http.HttpStatus;

import java.util.List;

public record PsychologyTestsAllResponseDto (List<PsychologicalTest> content, HttpStatus httpStatus){
}
