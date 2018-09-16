package com.template.services;

import com.infra.exceptions.ServiceException;
import com.template.services.TemplateService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.ServiceUnavailableException;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class TemplateIServiceTest {

    private TemplateService templateService = new TemplateService();

    @Test
    public void helloWorldTest() {
        Assert.assertEquals("templateService.helloWorld test failed", templateService.helloWorld(Optional.empty()), "Hello world");
    }

    @Test
    public void helloWorldWithNameTest() {
        Assert.assertEquals("templateService.helloWorld test failed", templateService.helloWorld(Optional.of("bar")), "Hello bar");
    }

    @Test
    public void healthCheckTest() {
        templateService.healthCheck();
    }

    @Test(expected = ServiceUnavailableException.class)
    public void maintenanceErrorTest() throws Exception {
        templateService.maintenanceError();
        throw new RuntimeException();
    }

    @Test(expected = ServiceException.class)
    public void internalServerErrorTest() throws Exception {
        templateService.internalServerError();
        throw new RuntimeException();
    }
}
