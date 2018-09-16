package com.template.controllers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

public class HttpServerErrorExceptionMatcher extends TypeSafeMatcher<HttpServerErrorException> {

    private HttpStatus code;

    public HttpServerErrorExceptionMatcher(HttpStatus code) {
        this.code = code;
    }

    @Override
    protected boolean matchesSafely(HttpServerErrorException item) {
        return item.getStatusCode() == this.code;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("expects code ")
                .appendValue(code);
    }
}
