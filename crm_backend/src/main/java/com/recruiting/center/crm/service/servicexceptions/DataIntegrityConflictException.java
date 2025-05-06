package com.recruiting.center.crm.service.servicexceptions;

public class DataIntegrityConflictException extends RuntimeException {
    public DataIntegrityConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
