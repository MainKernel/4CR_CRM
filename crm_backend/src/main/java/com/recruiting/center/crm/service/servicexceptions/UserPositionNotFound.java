package com.recruiting.center.crm.service.servicexceptions;

public class UserPositionNotFound extends RuntimeException {
    public UserPositionNotFound(String message) {
        super(message);
    }
}
