package com.infra.validators.entities;


import java.util.regex.Pattern;

public class HeaderValidationData {
    private String regex;

    private Pattern pattern;

    private boolean isRequired;

    private String headerName;

    public HeaderValidationData(String headerName, String regex, boolean isRequired) {
        this.headerName = headerName;
        this.regex = regex;
        this.isRequired = isRequired;
        this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    public String getRegex() {
        return regex;
    }

    public String getHeaderName() {
        return headerName;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
