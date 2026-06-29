package com.niladri.lovable_app.exceptions;

public class UserIsNotInMemberListException extends RuntimeException {
    public UserIsNotInMemberListException(String message) {
        super(message);
    }
}
