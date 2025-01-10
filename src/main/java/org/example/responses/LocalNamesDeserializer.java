package org.example.responses;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.Map;

/**
 *  to use that method need to add:
 *  1. in CoordinatesByName.class
 *     @JsonDeserialize(using = LocalNamesDeserializer.class)
 *     @JsonProperty("local_names")
 *     private Map<String, String> localNames;
 *  and
 *    // getters and setters
 *    public Map<String, String> getLocalNames() {
 *         return localNames;
 *     }
 */
public class LocalNamesDeserializer extends JsonDeserializer<Map<String, String>> {
    @Override
    public Map<String, String> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        // Custom deserialization logic
        return p.readValueAs(Map.class);
    }
}