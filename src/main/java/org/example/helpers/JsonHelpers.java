package org.example.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import io.restassured.response.Response;
import org.example.responses.*;
import org.example.responses.Error;
import org.json.*;
import java.util.List;

public class JsonHelpers {
    private void jsonAsExampleWithArray() {
        JSONObject json = new JSONObject();
        // basic as simple
        json.put("firstName", "John");
        json.put("lastName", "Smith");
        json.put("age", 25);
        // address as object
        JSONObject address = new JSONObject();
        address.put("streetAddress", "21 2nd Street");
        address.put("city", "New York");
        address.put("state", "NY");
        address.put("postalCode", "10021");
        json.put("address", address);
        // phone as array
        JSONArray tels = new JSONArray();
        JSONObject tel1 = new JSONObject();
        tel1.put("type", "home");
        tel1.put("number", "212 555-1234");
        JSONObject tel2 = new JSONObject();
        tel2.put("type", "fax");
        tel2.put("number", "646 555-4567");
        tels.put(tel1);
        tels.put(tel2);
        json.put("phoneNumber", tels);

        // to get string
        System.out.println(json.get("firstName") + " and " + json.get("lastName"));

        // to get number
        int age = json.getInt("age");
        System.out.println("age by 'getInt' - " + age);
        // OR
        age = Integer.parseInt(json.getNumber("age").toString());
        System.out.println("age by 'parseInt' - " + age);

        // to get object
        JSONObject address2 = json.getJSONObject("address");
        System.out.println(address2.getString("city"));

        // to get array
        JSONArray tels2 = json.getJSONArray("phoneNumber");
        JSONObject tel12 = tels2.getJSONObject(0);
        String number2 = tel12.getString("number");
        System.out.println(number2);
        // OR
        number2 = json.getJSONArray("phoneNumber").getJSONObject(0).getString("number");
        System.out.println(number2);
    }

    private void jsonWithName(String name) {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("q", name);
        jsonRequest.put("appid", ApiKeyHelpers.getMyFirstKey());
    }

    private void jsonWithZip(String zip) {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("zip", zip);
        jsonRequest.put("appid", ApiKeyHelpers.getMyFirstKey());
    }

    public static List<CoordinatesByName> jsonObjectMapperConversionWithTry(Response response) {
        ObjectMapper mapper = new ObjectMapper();
        // globally to ignore unknown properties
        // mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // OR
        // locally to ignore unknown properties in CoordinatesByName '@JsonIgnoreProperties(ignoreUnknown = true)'
        List<CoordinatesByName> coordinatesByNames = null;
        try {
            coordinatesByNames = mapper.readValue(response.asString(), new TypeReference<List<CoordinatesByName>>() {});
            // or can use just array or to convert to List
            // coordinatesByNames = List.of(mapper.readValue(response.asString(), CoordinatesByName[].class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return coordinatesByNames;
    }

    public static List<CoordinatesByName> jsonObjectMapperConversion(Response response) throws JsonProcessingException {
        return new ObjectMapper().readValue(response.asString(), new TypeReference<List<CoordinatesByName>>() {});
    }

    public static CoordinatesByZip jsonSimpleConversion(Response response) {
        return response.getBody().as(CoordinatesByZip.class);
    }

    public static Error jsonErrorSimpleConversion(Response response) {
        return response.getBody().as(Error.class);
    }
}