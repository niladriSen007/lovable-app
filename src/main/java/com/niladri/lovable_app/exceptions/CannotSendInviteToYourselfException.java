package com.niladri.lovable_app.exceptions;

public class CannotSendInviteToYourselfException extends RuntimeException {
    public CannotSendInviteToYourselfException(String message) {
        super(message);
    }
}
