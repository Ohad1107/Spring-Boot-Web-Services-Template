package com.infra.interceptors;

import com.infra.context.ContextHandler;
import com.infra.validators.IHttpHeadersValidator;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestInterceptor implements HandlerInterceptor {

    private IHttpHeadersValidator httpHeadersValidator;

    public RequestInterceptor(IHttpHeadersValidator httpHeadersValidator){
        this.httpHeadersValidator = httpHeadersValidator;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ContextHandler.setContextFromRequestHeaders(request);
        httpHeadersValidator.validateHeaders(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
