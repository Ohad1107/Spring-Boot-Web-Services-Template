package com.infra.clients;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class ProxyHelper {

    public static RestTemplate buildRestTemplate(String proxy) {
        if (proxy != null && !proxy.isEmpty()) {
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            String[] _proxy = proxy.split(":");
            requestFactory.setProxy(
                    new Proxy(Proxy.Type.HTTP, new InetSocketAddress(_proxy[0], Integer.parseInt(_proxy[1])))
            );
            return new InternalRestClient(requestFactory);
        } else {
            return new InternalRestClient();
        }
    }

    public static String buildURL(String url, String suffix) {
        return url + (url.endsWith("/") ? "" : "/") + suffix;
    }
}
