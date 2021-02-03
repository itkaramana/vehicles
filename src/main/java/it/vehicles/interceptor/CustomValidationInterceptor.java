package it.vehicles.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import it.vehicles.exceptions.BadRequestException;
import it.vehicles.util.Constant;

public class CustomValidationInterceptor extends HandlerInterceptorAdapter {

	private enum PossibleQueryParams {
		PRICEMIN("price[min]"), PRICEMAX("price[max]"), PAGELIMIT("page[limit]"), PAGEOFFSET("page[offset]"), IDS(
				"ids"), SORT("sort"), NEAR("near");

		private String value;

		public String getValue() {
			return this.value;
		}

		private PossibleQueryParams(String value) {
			this.value = value;
		}

		public static boolean containsValue(String requestValue) {
			for (PossibleQueryParams priceValues : values()) {
				if (priceValues.getValue().equals(requestValue)) {
					return true;
				}
			}
			return false;
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (request.getMethod().equals("GET")) {
			Map<String, String[]> parameters = request.getParameterMap();
			if (parameters.size() > 0) {
				for (String parameterName : parameters.keySet()) {
					if (!PossibleQueryParams.containsValue(parameterName)) {
						throw new BadRequestException(
								Constant.ERROR_UNRECOGNIZED_PARAMETER.replace(Constant.PARAMETER_NAME, parameterName),
								Constant.ERROR_400_BADREQUEST);
					}
				}
			}
		}
		return true;
	}

}
