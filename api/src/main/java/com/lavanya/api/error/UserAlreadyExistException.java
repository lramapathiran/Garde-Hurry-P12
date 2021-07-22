package com.lavanya.api.error;

/**
 * Custom Exceptions to throw when a user already exists in database.
 * Exception required when saving a new user in database with the form in addUser.html
 * @author lavanya
 */
public final class UserAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public UserAlreadyExistException() {
        super();
    }

    public UserAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistException(final String message) {
        super(message);
    }

    public UserAlreadyExistException(final Throwable cause) {
        super(cause);
    }

}
