package com.template;

import com.infra.interceptors.RequestInterceptor;
import com.infra.validators.HttpHeadersValidator;
import com.infra.validators.entities.HeaderValidationData;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;

@Configuration
@ComponentScan("com")
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestInterceptor(new HttpHeadersValidator(new ArrayList<HeaderValidationData>())));
    }
}
