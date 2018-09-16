package com.infra.monitoring;

import org.springframework.boot.actuate.metrics.writer.MetricWriter;

public interface IMetricWriter extends MetricWriter {

    void incrementIncomingRequest(String action, String method, double value, String code, String client);

    void setIncomingRequest(String action, String method, double value, String code);

    void incrementOutgoinglRequest(String action, String method, double value, String code, String destination);

    void setOutgoinglRequest(String action, String method, double value, String code);

    void increment(String action, String method, double value, String code);

    void setPerformanceMonitor(String action, String method, double value, String code);
}
