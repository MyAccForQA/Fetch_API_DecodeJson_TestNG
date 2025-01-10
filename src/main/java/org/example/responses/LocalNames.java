package org.example.responses;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalNames {
    @JsonProperty("en")
    private String en;
    @JsonProperty("uk")
    private String uk;
    @JsonProperty("ascii")
    private String ascii;
    @JsonProperty("bg")
    private String bg;

    @Override
    public String toString() {
        return "Local_names: {en= " + en + ", uk=" + uk + "ascii=" + ascii + "}, bg=" + bg + "}";
    }

    // getters and setters
    public String getEn() {
        return en;
    }

    public String getUk() {
        return ascii;
    }

    public String getAscii() {
        return ascii;
    }

    public String getBg() {
        return bg;
    }
}