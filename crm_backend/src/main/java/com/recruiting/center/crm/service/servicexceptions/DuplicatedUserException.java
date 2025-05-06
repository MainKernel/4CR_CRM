package com.recruiting.center.crm.service.servicexceptions;

public class DuplicatedUserException extends RuntimeException {
    public DuplicatedUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
