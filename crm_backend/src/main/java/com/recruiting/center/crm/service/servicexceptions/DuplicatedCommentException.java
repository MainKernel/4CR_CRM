package com.recruiting.center.crm.service.servicexceptions;

public class DuplicatedCommentException extends RuntimeException {
    public DuplicatedCommentException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
