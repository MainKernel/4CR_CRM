package com.recruiting.center.crm.service.servicexceptions;

public class CandidateStatusException extends RuntimeException {
    public CandidateStatusException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
