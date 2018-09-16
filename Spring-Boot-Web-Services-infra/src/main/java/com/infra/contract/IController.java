package com.infra.contract;

import com.infra.exceptions.ServiceException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

public interface IController {

    @RequestMapping(value = "/readiness", method = RequestMethod.GET)
    void readiness() throws ServiceException, IOException;
    
    @RequestMapping(value = "/liveness", method = RequestMethod.GET)
    void liveness() throws ServiceException, IOException;
}

