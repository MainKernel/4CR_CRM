package com.recruiting.center.crm.service.servicexceptions;

public class UserPositionNotFoundException extends RuntimeException {
    public UserPositionNotFoundException(String message) {
        super(message);
    }
}
