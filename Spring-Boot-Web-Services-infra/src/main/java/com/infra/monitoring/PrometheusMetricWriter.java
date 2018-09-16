package com.infra.monitoring;

import io.prometheus.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.actuate.metrics.writer.Delta;

class PrometheusMetricWriter implements IMetricWriter {

    private static final String METHOD_LABEL = "method";
    private static final String CLIENT_LABEL = "client";
    private static final String ACTION_LABEL = "action";
    private static final String CODE_LABEL = "code";
    private static final String DESTINATION_LABEL = "destination";
    private static final String COUNT_STRING = "count";
    private static final String LATENCIES_STRING = "latencies";
    private static final String API_REQUEST_STRING = "api_request";
    private static final String REST_CLIENT = "restclient";
    private static final String PERFORMANCE_MONITOR = "performance_monitor";

    private final Counter counterIncomingRequest;
    private final Summary summaryIncomingRequest;

    private final Counter counterOutgoinglRequest;
    private final Summary summaryOutgoinglRequest;

    private final Counter counterPerformanceMonitor;
    private final Summary summaryPerformanceMonitor;

    private final String namespace = "temp_namespace";

    private CollectorRegistry registry;

    @Autowired
    public PrometheusMetricWriter(CollectorRegistry registry) {
        this.registry = registry;

        counterIncomingRequest = createCounter(API_REQUEST_STRING, CLIENT_LABEL, METHOD_LABEL, ACTION_LABEL, CODE_LABEL);

        summaryIncomingRequest = createSummary(API_REQUEST_STRING, METHOD_LABEL, ACTION_LABEL, CODE_LABEL);

        counterOutgoinglRequest = createCounter(REST_CLIENT, METHOD_LABEL, DESTINATION_LABEL, ACTION_LABEL, CODE_LABEL);

        summaryOutgoinglRequest = createSummary(REST_CLIENT, METHOD_LABEL, ACTION_LABEL, CODE_LABEL);

        counterPerformanceMonitor = createCounter(PERFORMANCE_MONITOR, CLIENT_LABEL, METHOD_LABEL, ACTION_LABEL, CODE_LABEL);

        summaryPerformanceMonitor = createSummary(PERFORMANCE_MONITOR, METHOD_LABEL, ACTION_LABEL, CODE_LABEL);
    }


    @Override
    public void incrementIncomingRequest(String action, String method, double value, String code, String client) {
        Counter.Child child = new Counter.Child();
        child.inc(value);
        counterIncomingRequest.setChild(child, client, method, action, code);
    }

    @Override
    public void setIncomingRequest(String action, String method, double value, String code) {
        summaryIncomingRequest.labels(method, action, code).observe(value);
    }

    @Override
    public void incrementOutgoinglRequest(String action, String method, double value, String code, String destination) {
        Counter.Child child = new Counter.Child();
        child.inc(value);
        counterOutgoinglRequest.setChild(child, method, destination, action, code);
    }

    @Override
    public void setOutgoinglRequest(String action, String method, double value, String code) {
        summaryOutgoinglRequest.labels(method, action, code).observe(value);
    }

    @Override
    public void increment(String action, String method, double value, String code) {
        Counter.Child child = new Counter.Child();
        child.inc(value);
        counterPerformanceMonitor.setChild(child, method, action, code);
    }

    @Override
    public void setPerformanceMonitor(String action, String method, double value, String code) {
        summaryPerformanceMonitor.labels(method, action, code).observe(value);
    }

    @Override
    public void increment(Delta<?> delta) {

    }

    @Override
    public void reset(String metricName) {
    }

    @Override
    public void set(Metric<?> value) {

    }

    private Summary createSummary(String resource, String... labels) {
        return Summary.build().
                name(buildMetricName(namespace, resource, LATENCIES_STRING)).
                help(buildMetricName(namespace, resource, LATENCIES_STRING)).
                labelNames(labels).register(registry);
    }

    private Counter createCounter(String resource, String... labels) {
        return Counter.build().
                name(buildMetricName(namespace, resource, COUNT_STRING)).
                help(buildMetricName(namespace, resource, COUNT_STRING)).
                labelNames(labels).register(registry);
    }

    private String buildMetricName(String namespace, String resource, String type) {
        return String.format("%s_%s_%s", namespace, resource, type);
    }
}