package com.niladri.lovable_app.exceptions;

public class ProjectNotExistException extends RuntimeException {
    public ProjectNotExistException(String message) {
        super(message);
    }
}
