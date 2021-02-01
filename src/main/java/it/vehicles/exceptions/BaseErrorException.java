package it.vehicles.exceptions;

import lombok.Getter;

@Getter
public class BaseErrorException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final Integer errorCode;
	private String description;

	public BaseErrorException(final String message, final Integer errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public BaseErrorException(final String message, final Integer errorCode, final String description) {
		super(message);
		this.errorCode = errorCode;
		this.description = description;
	}

	public BaseErrorException(final String message, final Integer errorCode, final Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public BaseErrorException(final String message, final Integer errorCode, final String description,
			final Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		this.description = description;
	}
}
