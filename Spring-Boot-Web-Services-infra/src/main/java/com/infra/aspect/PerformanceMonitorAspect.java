package com.infra.aspect;

import com.infra.monitoring.IMetricWriter;
import com.infra.monitoring.PerformanceMonitor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class PerformanceMonitorAspect {

	@Autowired
	private final IMetricWriter metricWriter;

	public PerformanceMonitorAspect(MetricWriter metricWriter) {
		this.metricWriter = (IMetricWriter) metricWriter;
	}

	@Around("@annotation(performanceMonitor)")
	public Object performanceMonitorAround(ProceedingJoinPoint pjp, PerformanceMonitor performanceMonitor)
	        throws Throwable {
		Object retVal = null;

		String status = "failure";
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		try {
			retVal = pjp.proceed();
			status = "success";
		} catch (Exception e) {
			metricWriter.increment(performanceMonitor.action(), performanceMonitor.method(), 1,
			        status);

			throw e;
		} finally {
			stopWatch.stop();
			metricWriter.setPerformanceMonitor(performanceMonitor.action(), performanceMonitor.method(),
			        stopWatch.getTotalTimeMillis(), status);
		}

		if (performanceMonitor.countSuccess()) {
			metricWriter.increment(performanceMonitor.action(), performanceMonitor.method(), 1,
			        status);
		}

		return retVal;
	}
}
