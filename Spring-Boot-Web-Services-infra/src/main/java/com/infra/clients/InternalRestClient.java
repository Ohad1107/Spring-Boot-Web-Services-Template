package com.infra.clients;

import com.infra.interceptors.CustomClientHttpRequestInterceptor;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class InternalRestClient extends RestTemplate {
    public InternalRestClient() {
        super();
        setInterceptors(Collections.singletonList(new CustomClientHttpRequestInterceptor()));
    }

    public InternalRestClient(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
        setInterceptors(Collections.singletonList(new CustomClientHttpRequestInterceptor()));
    }
}
