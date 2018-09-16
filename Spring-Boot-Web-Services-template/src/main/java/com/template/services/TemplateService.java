package com.template.services;

import com.infra.exceptions.ServiceException;
import com.template.controllers.TemplateController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;
import java.util.Optional;

@Service
public class TemplateService implements IService<String> {

    private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);

    @Override
    public String helloWorld(Optional<String> name) {
        logger.info("Entering hello world");
        return String.format("Hello %s", name.orElse("world"));
    }

    @Override
    public void healthCheck() {

        logger.info("healthCheck");
    }

    @Override
    public void maintenanceError() throws Exception {

        logger.info("maintenance-error");

        throw new ServiceUnavailableException("maintenance-error message from template micro-service");
    }

    @Override
    public void internalServerError() throws Exception {

        logger.info("internal-server-error");

        throw new ServiceException(1, "internal-server-error message from template micro-service");
    }
}
