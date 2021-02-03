package it.vehicles.util;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Error
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Error {

	private Integer code;

	private String reason;

	private String message;

	@JsonIgnore
	private Throwable cause;

	public static Error of(final String reason, final Integer code, Throwable cause) {
		return builder().reason(reason).code(code).cause(cause).build();
	}

	public static Error of(final String reason, final Integer code, final String message, Throwable cause) {
		return builder().reason(reason).code(code).message(message).cause(cause).build();
	}
}
