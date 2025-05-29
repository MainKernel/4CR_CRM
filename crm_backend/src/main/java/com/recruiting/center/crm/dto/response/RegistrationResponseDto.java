package com.recruiting.center.crm.dto.response;

import org.springframework.http.HttpStatus;

public record RegistrationResponseDto (String token, HttpStatus httpStatus){
}
