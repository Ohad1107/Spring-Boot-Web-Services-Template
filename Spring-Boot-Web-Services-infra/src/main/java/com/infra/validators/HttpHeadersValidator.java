package com.infra.validators;

import com.infra.exceptions.http.HttpBadRequestException;
import com.infra.validators.entities.HeaderValidationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class HttpHeadersValidator implements IHttpHeadersValidator {

    private final static Logger logger = LoggerFactory.getLogger(HttpHeadersValidator.class);

    private List<HeaderValidationData> headerValidationDatas;

    public HttpHeadersValidator(List<HeaderValidationData> headerValidationDatas) {
        this.headerValidationDatas = headerValidationDatas;
    }

    public void put(HeaderValidationData headerValidationData) {
        headerValidationDatas.add(headerValidationData);
    }

    @Override
    public void validateHeaders(HttpServletRequest request) {
        headerValidationDatas.forEach(headerValidationData -> {
            String headerValue = request.getHeader(headerValidationData.getHeaderName());

            if (headerValue != null && !headerValue.isEmpty()) {
                if (!headerValidationData.getPattern().matcher(headerValue).matches()) {

                    logger.warn("Header = %s with value = %s doesn't match the expected regex = %s. request = %s",
                            headerValidationData.getHeaderName(), headerValue, headerValidationData.getRegex(), request.getRequestURI());

                    throw new HttpBadRequestException();
                }
            } else if (headerValidationData.isRequired()) {

                logger.warn("Header = %s doesn't exist in the request = %s", headerValidationData.getHeaderName(), request.getRequestURI());
                
                throw new HttpBadRequestException();
            }
        });
    }
}
