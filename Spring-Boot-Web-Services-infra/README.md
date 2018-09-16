
This repository contains general purpose documentation about MicroServices infra in TU Go.

## Getting started
    Install gradle on your machine
    Install Java 8 on your machine

## How to build a Micro Service infra
    Go to your 'micro-service-infra' workspace
    Execute:
        gradle build

    gradle will build your project (jar location build/libs) and will run the project's unit tests (currently we have 1 test).
    The build will fail in case one or more tests failed (you can find logs are stored in logs/ folder).

    To see the infra project tests results go to logs/

## Project Features
    The project contains the basic features micro-service needs.

    The features are:
    1. [Thread Context](https://pdihub.hi.inet/tugo/tugo-microservices/tree/master/micro-service-infra/src/main/java/com/infra/context) - stores info (user id, component, partner id etc.) on the thread local variables.
    2. Writes log event before the API starts and after the API ends ([see RequestInterceptor](https://pdihub.hi.inet/tugo/tugo-microservices/blob/master/micro-service-infra/src/main/java/com/infra/interceptors/RequestInterceptor.java))
    3. In the beginning of the API, the RequestInterceptor sets the thread context info from the request's http headers.
    4. Generates new correlator in case correlator doesn't exists in the request's http headers.
    5. Adds to every log event the thread context.

## Project Contract
   Contains [Controller](https://pdihub.hi.inet/tugo/tugo-microservices/blob/master/micro-service-infra/src/main/java/com/infra/contract/Controller.java) interface that every micro-service must implement.
