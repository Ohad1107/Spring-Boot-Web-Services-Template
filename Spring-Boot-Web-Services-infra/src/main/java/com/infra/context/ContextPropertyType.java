package com.infra.context;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum ContextPropertyType {
    ORIGIN("Origin", "Origin"),
    PARTNER_ID("Partner-Id", "PID"),
    EXTERNAL_TRANSACTION_ID("Transaction-Id", "TID"),
    API("API", "API"),
    USER_ID("User-ID", "UID"),
    USER_AGENT("User-Agent", "UA");


    private final String text;
    @Getter private final String key;

    ContextPropertyType(final String key, final String text) {
        this.key = key;
    	this.text = text;       
    }

    @Override
    public String toString() {
        return text;
    }

    @JsonValue
    public String toValue() {
        return toString();
    }
}
