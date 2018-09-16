package com.template.controllers;

import com.template.services.IService;
import com.infra.contract.IController;
import com.infra.exceptions.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("v1/template")
public class TemplateController implements IController {

    private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);

    private IService<String> service;

    @Autowired
    public TemplateController(IService<String> service) {
        this.service = service;
    }

	@Override
    @RequestMapping(value = {"/helloWorld", "/helloWorld/{name}"}, method = RequestMethod.GET)
	public void readiness() throws ServiceException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
    @RequestMapping(value = {"/helloWorld", "/helloWorld/{name}"}, method = RequestMethod.GET)
	public void liveness() throws ServiceException, IOException {
		// TODO Auto-generated method stub
		
	}
}
