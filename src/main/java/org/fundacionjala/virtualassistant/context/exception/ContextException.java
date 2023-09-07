package org.fundacionjala.virtualassistant.context.exception;

public class ContextException extends Exception{

    public static final String MESSAGE_CONTEXT_NULL = "The object Context is null";
    public static final String MESSAGE_CONTEXT_REQUEST_NULL = "The Context request is null";

    public ContextException(String message) {
        super(message);
    }

    public ContextException(String message, Throwable cause) {
        super(message, cause);
    }
}