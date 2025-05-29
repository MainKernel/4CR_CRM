package com.recruiting.center.crm.dto.response;

import org.springframework.http.HttpStatus;

public record LoginResponseDto (String token, HttpStatus httpStatus) {
}
