package com.infra.validators;


import javax.servlet.http.HttpServletRequest;

public interface IHttpHeadersValidator {

    void validateHeaders(HttpServletRequest request);
}
