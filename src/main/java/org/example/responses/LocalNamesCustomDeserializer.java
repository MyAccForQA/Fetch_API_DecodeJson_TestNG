package org.example.responses;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.Map;

/**
 *  to use that method need to add:
 *  1. in LocalNames.class
 *     @JsonDeserialize(using = LocalNamesCustomDeserializer.class)
 *     // @JsonProperty("local_names")
 *     private Map<String, String> localNames;
 *  and
 *     // getters and setters
 *     public Map<String, String> getLocalNames() {
 *         return localNames;
 *     }
 *
 *     public void setLocalNames(Map<String, String> localNames) {
 *         this.localNames = localNames;
 *     }
 */
public class LocalNamesCustomDeserializer extends JsonDeserializer<LocalNames> {
    @Override
    public LocalNames deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        // Read the JSON data as a Map
        Map<String, String> namesMap = p.readValueAs(Map.class);

        // Create a new LocalNames instance and set the Map
        LocalNames localNames = new LocalNames();
        // localNames.setLocalNames(namesMap); // commented since not exist

        return localNames;
    }
}