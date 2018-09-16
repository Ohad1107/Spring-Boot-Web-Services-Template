package com.infra.context;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class ContextHandler {

	private static final ThreadLocal<Context> userThreadLocal = new ThreadLocal<>();

	public static void set(Context context) {
		userThreadLocal.set(context);
	}

	public static void unset() {
		userThreadLocal.remove();
	}

	public static Context get() {
		return userThreadLocal.get();
	}

	public static void setContextFromRequestHeaders(HttpServletRequest request) {
		Context context = userThreadLocal.get() != null ? userThreadLocal.get() : new ThreadContext();

		for (ContextPropertyType type : ContextPropertyType.values()) {
			context.setPropertyValue(type, request.getHeader(type.getKey()));
		}

		userThreadLocal.set(context);
	}

	public static Map<String, String> setInternalRequestHeadersFromContext(
	        Map<String, ContextPropertyType> responseHeaderMap) {
		Context context = ContextHandler.get();
		Map<String, String> values = new HashMap<>();

		if (context != null) {
			responseHeaderMap.entrySet().stream().filter(map -> context.containsProperty(map.getValue()))
			        .forEach(item -> {
				        if (context.containsProperty(item.getValue())) {
					        values.put(item.getKey(), context.getPropertyValue(item.getValue(), String.class));
				        }
			        });
		}
		return values;
	}
}
