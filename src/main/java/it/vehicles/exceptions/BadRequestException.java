package it.vehicles.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends BaseErrorException {

	private static final long serialVersionUID = 1L;

	public BadRequestException(final String message, final Integer errorCode) {
		super(message, errorCode);
	}

	public BadRequestException(final String message, final Integer errorCode, final String description) {
		super(message, errorCode, description);
	}

	public BadRequestException(final String message, final Integer errorCode, final Throwable cause) {
		super(message, errorCode, cause);
	}

	public BadRequestException(final String message, final Integer errorCode, final String description,
			final Throwable cause) {
		super(message, errorCode, description, cause);
	}
}
