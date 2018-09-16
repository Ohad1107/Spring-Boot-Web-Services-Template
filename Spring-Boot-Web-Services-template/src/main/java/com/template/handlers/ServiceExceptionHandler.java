package com.template.handlers;

import com.infra.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.naming.ServiceUnavailableException;

@ControllerAdvice
public class ServiceExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "maintenance")  // 503
    @ExceptionHandler(ServiceUnavailableException.class)
    public void handleServiceUnavailableException(Exception ex) {
        logger.warn("Got maintenance error:", ex);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "internal server error")  // 500
    @ExceptionHandler(ServiceException.class)
    public void handleServiceException(Exception ex) {
        logger.warn("Got internal error:", ex);
    }
}
