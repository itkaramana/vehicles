package it.vehicles.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseErrorException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(final String message, final Integer errorCode) {
		super(message, errorCode);
	}

	public NotFoundException(final String message, final Integer errorCode, final String description) {
		super(message, errorCode, description);
	}

	public NotFoundException(final String message, final Integer errorCode, final Throwable cause) {
		super(message, errorCode, cause);
	}

	public NotFoundException(final String message, final Integer errorCode, final String description,
			final Throwable cause) {
		super(message, errorCode, description, cause);
	}

}
