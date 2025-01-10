package org.example.responses;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Error {
    @JsonProperty("cod")
    private String cod;
    @JsonProperty("message")
    private String message;

    @Override
    public String toString() {
        return "Error: {cod=" + cod + ", message=" + message + "}";
    }

    // getters and setters
    public String getCod() {
        return cod;
    }

    public String getMessage() {
        return message;
    }
}