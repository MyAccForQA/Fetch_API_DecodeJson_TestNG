package org.example.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpHelpers {
    public static Response MakeApiCall(String endPoint) {
        return MakeApiCall(endPoint, HttpMethod.GET);
    }

    public static Response MakeApiCall(String endPoint, HttpMethod httpMethod) {
        return MakeApiCall(endPoint, httpMethod, null);
    }

    public static Response MakeApiCall(String endPoint, HttpStatusCode expectedStatusCode) {
        return MakeApiCall(endPoint, HttpMethod.GET, null, expectedStatusCode);
    }

    public static Response MakeApiCall(String endPoint, HttpMethod httpMethod, HttpStatusCode expectedStatusCode) {
        return MakeApiCall(endPoint, httpMethod, null, expectedStatusCode);
    }

    public static Response MakeApiCall(String endPoint, HttpMethod httpMethod, JSONObject jsonRequest, HttpStatusCode expectedStatusCode) {
        return MakeApiCall(endPoint, httpMethod, jsonRequest, null, expectedStatusCode);
    }

    public static Response MakeApiCall(String endPoint, HttpMethod httpMethod, JSONObject jsonRequest, String accessToken, HttpStatusCode expectedStatusCode) {
        RestAssured.baseURI = UrlHelpers.getBasicURL();
        RequestSpecification request = RestAssured.given();
        // request.header("Content-Type", "application/json");

        // method of request
        Response response = switch (httpMethod.getMethod()) {
            case "GET" -> request.get(endPoint);
            case "POST" -> request.post(endPoint);
            default -> throw new IllegalStateException("Unexpected value: " + httpMethod.getMethod());
        };

        // default is HttpStatusCode.OK and should send HttpStatusCode.BAD_REQUEST if need to verify with error
        if (expectedStatusCode != null && response.getStatusCode() != expectedStatusCode.getStatusCode()) {
            // need to call some exception
            System.out.println("\nError in HttpHelpers.MakeApiCall(). 'HttpStatusCode' is NOT right!!!");
            System.out.println("Need to call some exception!!!");
        }
        return response;
    }

    public static Response MakeApiCall_notUse(String endPoint, HttpMethod httpMethod, JSONObject jsonRequest, String accessToken, HttpStatusCode expectedStatusCode) {
        // did not get body
        String jsonBody = "{\"key1\":\"value1\",\"key2\":\"value2\"}"; // JSON body
        Response response = null;
        StringBuilder responseBody;

        try {
            // append parameters to the URL
            URL url = new URL(UrlHelpers.getBasicURL() + endPoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(httpMethod.getMethod());
            // connection.setRequestProperty("Content-Type", "application/json");

            // settings
            connection.setUseCaches(false);
            connection.setDoOutput(true); // enable sending data

            // write the body to the output stream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // default is HttpStatusCode.OK and should send HttpStatusCode.BAD_REQUEST if need to verify with error
            if (connection.getResponseCode() != expectedStatusCode.getStatusCode()) {
                // need to call some exception
                // System.out.println("\nError in HttpHelpers.MakeApiCall(). 'HttpStatusCode' is NOT right!!!");
                // System.out.println("Need to call some exception!!!");
            }

            // read the response body
            InputStream responseStream = connection.getInputStream();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream, StandardCharsets.UTF_8))) {
                responseBody = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }
            }
            response = new ObjectMapper().readValue(responseBody.toString(), Response.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}