package org.example.responses;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Coordinates {
    @JsonProperty("name")
    private String name;

    @Override
    public String toString() {
        return "Coordinates by name {name=" + getName() + "}";
    }

    // getters and setters
    public String getName() {
        return name;
    }
}