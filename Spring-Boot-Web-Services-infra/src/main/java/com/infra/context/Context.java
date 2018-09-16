package com.infra.context;

public interface Context {

    boolean isEmpty();

    boolean containsProperty(ContextPropertyType propertyKey);

    <T> T getPropertyValue(ContextPropertyType propertyKey, Class<T> valueType);

    <T> void setPropertyValue(ContextPropertyType propertyKey, T value);

    void removePropertyValue(ContextPropertyType propertyKey);

    String toStringNonMandatoryProperties();
    
    String toStringAllProperties();

}
