package com.recruiting.center.crm.service.servicexceptions;

public class CandidateStatusNotFoundException extends RuntimeException {
    public CandidateStatusNotFoundException(String message) {
        super(message);
    }
}
