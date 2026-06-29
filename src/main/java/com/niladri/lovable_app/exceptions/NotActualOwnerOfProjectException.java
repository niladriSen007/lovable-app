package com.niladri.lovable_app.exceptions;

public class NotActualOwnerOfProjectException extends RuntimeException {
    public NotActualOwnerOfProjectException(String message) {
        super(message);
    }
}
