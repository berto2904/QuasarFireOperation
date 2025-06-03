package org.meli.exception;

public class MessageConflictException extends RuntimeException {
    public MessageConflictException(String message) {
        super(message);
    }
}
