package com.infra.monitoring;

import java.lang.annotation.*;


/**
 * Annotation for performance monitor. Add this to a method in a class if you want to write th following metrics:
 * 1. Method execution time in milliseconds.
 * 2. Method success (exception was not thrown) count
 * 3. Method failure count
 *
 * The metric name us build from the @action and from the @method.
 * Make sure your @action name + @method name is unique
 * @action should be the class name or the target component name (for exampe: STS, BES, etc.),
 * @method should be the method name + params (params names are needed in case the method is been overloaded)
 *
 * @see com.infra.aspect.PerformanceMonitorAspect
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PerformanceMonitor {
    String action();

    String method();

    boolean countSuccess() default false;
}
