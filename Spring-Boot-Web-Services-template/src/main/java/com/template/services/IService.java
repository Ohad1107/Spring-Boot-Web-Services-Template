package com.template.services;

import java.util.Optional;

public interface IService<T> {
    T helloWorld(Optional<T> name);

    void healthCheck();

    void maintenanceError() throws Exception;

    void internalServerError() throws Exception;
}
