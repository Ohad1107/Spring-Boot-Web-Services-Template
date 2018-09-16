package com.template;

import com.infra.context.Context;
import com.infra.context.ContextHandler;
import com.infra.context.ContextPropertyType;
import com.infra.context.ThreadContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class MicroserviceTemplateApplication {

	public static void main(String[] args) {
		ContextHandler.set(createContext());
		SpringApplication.run(MicroserviceTemplateApplication.class, args);
	}

	public static Context createContext(){
		ThreadContext context = new ThreadContext();

		context.setPropertyValue(ContextPropertyType.ORIGIN, "template-microservice");
		context.setPropertyValue(ContextPropertyType.API, "Init");
		context.setPropertyValue(ContextPropertyType.EXTERNAL_TRANSACTION_ID, UUID.randomUUID().toString());

		return context;
	}
}
