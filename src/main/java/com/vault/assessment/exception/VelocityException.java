package com.vault.assessment.exception;

public class VelocityException extends Exception{

    public VelocityException() {
    }

    public VelocityException(String message) {
        super(message);
    }

    public VelocityException(Throwable cause) {
        super(cause);
    }

    public VelocityException(String message, Throwable cause) {
        super(message, cause);
    }
}
