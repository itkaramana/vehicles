package it.vehicles.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends BaseErrorException {

	private static final long serialVersionUID = 1L;

	public InternalServerErrorException(final String message, final Integer errorCode) {
		super(message, errorCode);
	}

	public InternalServerErrorException(final String message, final Integer errorCode, final String description) {
		super(message, errorCode, description);
	}

	public InternalServerErrorException(final String message, final Integer errorCode, final Throwable cause) {
		super(message, errorCode, cause);
	}

	public InternalServerErrorException(final String message, final Integer errorCode, final String description,
			final Throwable cause) {
		super(message, errorCode, description, cause);
	}
}