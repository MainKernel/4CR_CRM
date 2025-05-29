package com.recruiting.center.crm.service.servicexceptions;

public class CandidatePositionNotFoundException extends RuntimeException {
    public CandidatePositionNotFoundException(String message) {
        super(message);
    }
}
