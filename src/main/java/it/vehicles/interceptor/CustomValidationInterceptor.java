package it.vehicles.interceptor;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import it.vehicles.exceptions.BadRequestException;
import it.vehicles.util.Constant;

public class CustomValidationInterceptor extends HandlerInterceptorAdapter {

	private static ArrayList<String> parameters;

	static {
		parameters.add("price[min]");
		parameters.add("price[max]");
		parameters.add("page[limit]");
		parameters.add("page[offset]");
		parameters.add("ids");
		parameters.add("sort");
		parameters.add("near");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (request.getMethod().equals("GET")) {
			Map<String, String[]> parametersInRequest = request.getParameterMap();
			if (parametersInRequest.size() > 0) {
				for (String parameterName : parametersInRequest.keySet()) {
					if (!parameters.contains(parameterName)) {
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
