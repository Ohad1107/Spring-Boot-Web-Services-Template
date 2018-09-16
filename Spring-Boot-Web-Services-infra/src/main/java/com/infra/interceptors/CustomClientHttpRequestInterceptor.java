package com.infra.interceptors;

import com.infra.context.ContextHandler;
import com.infra.context.ContextPropertyType;
import com.infra.monitoring.IMetricWriter;

import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

	private static IMetricWriter metricWriter;

	@Setter private Map<String, ContextPropertyType> headersForOutgoingRequest = new HashMap<String, ContextPropertyType> ();
	
	public CustomClientHttpRequestInterceptor() {
	}

	@Autowired
	public void setMetricWriter(IMetricWriter metricWriter) {
		CustomClientHttpRequestInterceptor.metricWriter = metricWriter;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
	        throws IOException {
		request.getHeaders().setAll(ContextHandler.setInternalRequestHeadersFromContext(headersForOutgoingRequest));

		ClientHttpResponse response = null;
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		try {
			response = execution.execute(request, body);
		} finally {
			stopWatch.stop();

			if (request != null && response != null) {
				metricWriter.setOutgoinglRequest(request.getURI().getPath(), request.getMethod().toString(),
				        stopWatch.getTotalTimeMillis(), String.valueOf(response.getStatusCode()));

				metricWriter.incrementOutgoinglRequest(request.getURI().getPath(), request.getMethod().toString(), 1,
				        String.valueOf(response.getStatusCode()), request.getURI().getHost());// increment failure
				                                                                              // counter
			}
		}
		return response;
	}
}
