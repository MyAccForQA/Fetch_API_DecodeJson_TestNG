package org.example.responses;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoordinatesByZip extends Coordinates {
    @JsonProperty("zip")
    private String zip;
    @JsonProperty("lat")
    private double lat;
    @JsonProperty("lon")
    private double lon;
    @JsonProperty("country")
    private String country;

    @Override
    public String toString() {
        return "Coordinates by zip: {zip=" + zip + ", name=" + getName() + ", lat=" + lat + ", lon=" + lon + ", country=" + country + "}";
    }

    // getters and setters
    public String getZip() {
        return zip;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getCountry() {
        return country;
    }
}