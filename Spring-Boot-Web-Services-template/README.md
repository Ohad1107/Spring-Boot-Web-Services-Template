
This repository contains general purpose documentation about MicroServices template project in TU Go.

## Getting started
    Install gradle on your machine
    Install Java 8 on your machine

## How to build a Micro Service Template Project
    Go to your 'micro-service-template-project' workspace
    Execute:
        gradle build

    gradle will build your project (jar location build/libs) and 'micro-service-infra' project. It will run the project's unit tests and the 'micro-service-infra' project's unit tests.
    The build will fail in case one or more tests failed (you can find logs are stored in logs/ folder).

    To run the template project execute:
        java -jar build/libs/template-0.0.1-SNAPSHOT.jar
        To see the template project logs go to build/libs/logs.

## Template Project
    The template project is your start point to build your own micro-service, it contains:
    1. TemplateController (service layer) implements [Controller](https://pdihub.hi.inet/tugo/tugo-microservices/blob/master/micro-service-infra/src/main/java/com/infra/contract/Controller.java) that every micro-service must implement. This project also contains 'helloWorld' and 'raiseError' APIs.
    2. TemplateManager (business layer) implement Manager - this is the entry point for your business logic.
    3. AppConfig extends WebMvcConfigurerAdapter - adds interceptors to our service. For example to use the infra project's [RequestInterceptor](https://pdihub.hi.inet/tugo/tugo-microservices/blob/master/micro-service-infra/src/main/java/com/infra/interceptors/RequestInterceptor.java).
    4. Unit Tests - for every layer we should have unit tests. in our project we have for the TemplateController (service layer) and for the TemplateManager (business layer).