package me.nithanim.urlplaceholder.exceptions;

public abstract class ParameterException extends RuntimeException {
    public ParameterException(String message) {
        super(message);
    }
}
