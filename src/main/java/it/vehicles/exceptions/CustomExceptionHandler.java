package it.vehicles.exceptions;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ResponseEntity.status;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.vehicles.util.Constant;
import it.vehicles.util.Error;

@ControllerAdvice
@Order
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Pattern UNRECOGNIZED_FIELD_PATTERN = Pattern.compile("Unrecognized field \"([^\"]*)\"");

	@Override
	public ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		if (ex.getMessage() != null) {
			final Matcher m = UNRECOGNIZED_FIELD_PATTERN.matcher(ex.getMessage());
			if (m.find()) {
				final Error error = Error.of("Unrecognized parameters", 693,
						"#parameter-name# unrecognized".replace("#parameter-name#", m.group(1)), ex);
				return badRequest(() -> error);
			}
		}
		return badRequest(() -> null);
	}

	@ExceptionHandler(BaseErrorException.class)
	public ResponseEntity<Error> baseErrorException(final BaseErrorException ex, final WebRequest request) {
		return new ResponseEntity<Error>(
				Error.of(ex.getDescription(), ex.getErrorCode(), ex.getMessage(), ex.getCause()),
				responseStatus.apply(ex.getClass()));
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Error> handleAllExceptions(final Exception ex, final WebRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Error.of(ex.getMessage(), Constant.ERROR_500_CODE, Constant.ERROR_500_MESSAGE, ex));
	}

	public static final Function<Class<? extends BaseErrorException>, HttpStatus> responseStatus = c -> Optional
			.ofNullable(c.getDeclaredAnnotation(ResponseStatus.class)).map(ResponseStatus::value)
			.orElse(INTERNAL_SERVER_ERROR);

	public static <T> ResponseEntity<T> internalServerError(final Supplier<T> body) {
		return status(HttpStatus.INTERNAL_SERVER_ERROR).body(body.get());
	}

	public static <T> ResponseEntity<T> badRequest(final Supplier<T> body) {
		return status(HttpStatus.BAD_REQUEST).body(body.get());
	}
}
