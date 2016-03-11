package me.nithanim.urlplaceholder.exceptions;

public class ParameterAlreadyBoundException extends ParameterException {
    public ParameterAlreadyBoundException(String parameter) {
        super("Parameter \"" + parameter + "\" already bound!");
    }
}
