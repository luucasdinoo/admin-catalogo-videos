package com.dino.admin.catalogo.domain.exceptions;

public class InternalErrorException extends NoStackTraceException {

    protected InternalErrorException(final String aMessage, Throwable t) {
        super(aMessage, t);
    }

    public static InternalErrorException with(final String message, final Throwable t) {
        return new InternalErrorException(message, t);
    }
}
