package com.lavanya.api.error;

/**
 * Custom Exceptions to throw when a childrenToWatch was already added in the list of childcare.
 * Exception required when saving a new ChildrenToWatch from childcare in database
 * @author lavanya
 */
public class ChildrenToWatchAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public ChildrenToWatchAlreadyExistException() {
        super();
    }

    public ChildrenToWatchAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ChildrenToWatchAlreadyExistException(final String message) {
        super(message);
    }

    public ChildrenToWatchAlreadyExistException(final Throwable cause) {
        super(cause);
    }

}
