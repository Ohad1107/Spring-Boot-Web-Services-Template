package com.infra.logging;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;

import com.infra.context.Context;
import com.infra.context.ContextHandler;

@Plugin(name="ContextConverter", category = "Converter")
@ConverterKeys({"context"})
public class ContextConverter extends LogEventPatternConverter {

    protected ContextConverter(String name, String style) {
        super(name, style);
    }

    public static ContextConverter newInstance(String[] options) {
        return new ContextConverter("context",Thread.currentThread().getName());
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        toAppendTo.append(getContextInfo());
    }

    protected Context getContextInfo() {
        return ContextHandler.get();
    }

}
