package com.recruiting.center.crm.dto.response;

import com.recruiting.center.crm.entity.appuser.WorkUnit;
import org.springframework.http.HttpStatus;

import java.util.List;

public record WorkUnitsResponseDto (List<WorkUnit> content, HttpStatus httpStatus){
}
