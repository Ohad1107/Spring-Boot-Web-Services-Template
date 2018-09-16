package com.infra.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class ThreadContext implements Context {

	private static final Logger logger = LoggerFactory.getLogger(ThreadContext.class);
	private final static ArrayList<ContextPropertyType> toStringMandatoryProperties = new ArrayList<ContextPropertyType>() {
		private static final long serialVersionUID = 1L;
		{
			add(ContextPropertyType.API);
			add(ContextPropertyType.EXTERNAL_TRANSACTION_ID);
		}
	};

	private static ArrayList<ContextPropertyType> toStringNonMandatoryProperties = new ArrayList<ContextPropertyType>() {
		private static final long serialVersionUID = 1L;
		{
			addAll(EnumSet.allOf(ContextPropertyType.class));
			removeAll(toStringMandatoryProperties);
		}
	};

	private final Map<ContextPropertyType, Object> threadContextProperties = new HashMap<>();

	public ThreadContext() {
	}

	public boolean isEmpty() {
		return threadContextProperties.isEmpty();
	}

	public boolean containsProperty(ContextPropertyType propertyKey) {
		return threadContextProperties.containsKey(propertyKey) && threadContextProperties.get(propertyKey) != null;
	}

	public <T> T getPropertyValue(ContextPropertyType propertyKey, Class<T> valueType) {

		Object value = threadContextProperties.get(propertyKey);

		if (value == null) {
			logger.warn("Thread Context property '{}' doesn't exists", propertyKey);
			return null;
		}
		logger.debug("Thread Context property '{}' doesn't exists", propertyKey);
		return valueType.cast(value);
	}

	public <T> void setPropertyValue(ContextPropertyType propertyKey, T value) {
		threadContextProperties.put(propertyKey, value);
	}

	public void removePropertyValue(ContextPropertyType propertyKey) {
		threadContextProperties.remove(propertyKey);
	}

	@Override
	public String toString() {
		return toStringHelper(toStringMandatoryProperties);
	}

	@Override
	public String toStringNonMandatoryProperties() {
		return toStringHelper(toStringNonMandatoryProperties);
	}

	@Override
	public String toStringAllProperties() {
		return toStringHelper(Arrays.asList(ContextPropertyType.values()));
	}
	
	private String toStringHelper(List<ContextPropertyType> types) {
		StringBuilder stringBuilder = new StringBuilder();
		threadContextProperties.forEach((k, v) -> {
			if (types.contains(k)) {
				stringBuilder.append(k.toString() + "=[" + v + "]; ");
			}
		});
		return stringBuilder.toString().trim();
	}
}
