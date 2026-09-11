package com.lab.model;

public class GuidResponse {

    private boolean valid;
    private String message;
    private String normalizedGuid;

    public GuidResponse(boolean valid, String message, String normalizedGuid) {
        this.valid = valid;
        this.message = message;
        this.normalizedGuid = normalizedGuid;
    }

    public boolean isValid() {
        return valid;
    }

    public String getMessage() {
        return message;
    }

    public String getNormalizedGuid() {
        return normalizedGuid;
    }
}