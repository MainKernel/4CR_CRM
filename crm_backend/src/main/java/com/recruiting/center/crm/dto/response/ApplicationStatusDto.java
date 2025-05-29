package com.recruiting.center.crm.dto.response;

import org.springframework.http.HttpStatus;

public record ApplicationStatusDto(String appStatus, HttpStatus status) {
}
