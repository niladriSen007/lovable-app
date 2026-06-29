package com.niladri.lovable_app.exceptions;

public class UserIsAlreadyInMemberListException extends RuntimeException {
    public UserIsAlreadyInMemberListException(String message) {
        super(message);
    }
}
