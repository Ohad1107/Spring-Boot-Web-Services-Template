package com.infra.interceptors;

import com.infra.context.ContextHandler;
import com.infra.context.ContextPropertyType;
import com.infra.monitoring.IMetricWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.FilterChain;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomRequestFilter implements Filter {

    private final IMetricWriter metricWriter;

    @Autowired
    public CustomRequestFilter(MetricWriter metricWriter) {
        this.metricWriter = (IMetricWriter) metricWriter;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            chain.doFilter(req, res);
        } finally {
            stopWatch.stop();

            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;

            if (request != null && response != null) {
                metricWriter.setIncomingRequest(request.getRequestURI(), request.getMethod(), stopWatch.getTotalTimeMillis(), String.valueOf(response.getStatus()));

                metricWriter.incrementIncomingRequest(request.getRequestURI(), request.getMethod(), 1, String.valueOf(response.getStatus()), getUserAgent());
            }
        }
    }

    @Override
    public void destroy() {
    }

    private String getUserAgent(){
        return ContextHandler.get() != null && ContextHandler.get().containsProperty(ContextPropertyType.USER_AGENT) ?
                ContextHandler.get().getPropertyValue(ContextPropertyType.USER_AGENT, String.class) : "";
    }
}
