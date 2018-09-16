package com.template.controllers;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
public class TemplateControllerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private final RestTemplate restTemplate = new RestTemplate();

	@Value("${local.server.port}")
	private int port;

	@Test
	public void helloWorldTest() {
		String value = restTemplate.getForObject("http://localhost:" + port + "/v1/template/helloWorld/Foo",
		        String.class);
		Assert.assertEquals(value, "Hello Foo");
	}

	@Test
	public void helloWorldWithNameTest() {
		String value = restTemplate.getForObject("http://localhost:" + port + "/v1/template/helloWorld", String.class);
		Assert.assertEquals(value, "Hello world");
	}

	@Test
	public void healthCheckTest() {
		ResponseEntity<String> response = restTemplate
		        .getForEntity("http://localhost:" + port + "/v1/template/health-check", String.class);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void maintenanceErrorTest() {
		thrown.expect(new HttpServerErrorExceptionMatcher(HttpStatus.SERVICE_UNAVAILABLE));
		restTemplate.getForObject("http://localhost:" + port + "/v1/template/maintenance-error", String.class);
	}

	@Test
	public void internalServerErrorTest() {
		thrown.expect(new HttpServerErrorExceptionMatcher(HttpStatus.INTERNAL_SERVER_ERROR));
		restTemplate.getForObject("http://localhost:" + port + "/v1/template/internal-server-error", String.class);
	}
}
