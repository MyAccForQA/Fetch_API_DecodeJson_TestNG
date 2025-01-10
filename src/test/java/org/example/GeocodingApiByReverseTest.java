package org.example;

import io.restassured.response.Response;
import org.example.helpers.*;
import org.example.responses.CoordinatesByName;
import org.example.responses.Error;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class GeocodingApiByReverseTest {
    @BeforeMethod // before each test
    public void setUpBeforeMethod() {
        System.out.println("@BeforeMethod - to see in logs");
    }

    @AfterMethod // after each test
    public void setDownAfterMethod() {
        System.out.println("@AfterMethod - to see in logs");
    }

    @Test
    public void testCaseNo3_byReverse_skip() {
        /**
         * if no lat and lon - returns Nothing to geocode
         */
        // Arrange
        String lat = "";
        String lon = "";
        String expected = "Nothing to geocode";

        // Action
        Response reverseResponse = HttpHelpers.MakeApiCall(UrlHelpers.getReverseEndPoint() + UrlHelpers.getReverseParams(lat, lon), HttpMethod.GET, HttpStatusCode.BAD_REQUEST);
        Assert.assertEquals(reverseResponse.getStatusCode(), HttpStatusCode.BAD_REQUEST.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.BAD_REQUEST.getStatusCode());
        Error error = JsonHelpers.jsonErrorSimpleConversion(reverseResponse);

        // Assert
        Assert.assertEquals(error.getCod(), "" + HttpStatusCode.BAD_REQUEST.getStatusCode(), "message error - cod should be " + HttpStatusCode.BAD_REQUEST.getStatusCode());
        Assert.assertEquals(error.getMessage(), expected, "message error - message should be " + expected);
    }

    @Test
    public void testCaseNo3_byReverse_Empty() {
        /**
         * if lat and lon are empty - returns wrong latitude
         */
        // Arrange
        String lat = "''";
        String lon = "''";
        String expected = "wrong latitude";

        // Action
        Response reverseResponse = HttpHelpers.MakeApiCall(UrlHelpers.getReverseEndPoint() + UrlHelpers.getReverseParams(lat, lon), HttpMethod.GET, HttpStatusCode.BAD_REQUEST);
        Assert.assertEquals(reverseResponse.getStatusCode(), HttpStatusCode.BAD_REQUEST.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.BAD_REQUEST.getStatusCode());
        Error error = JsonHelpers.jsonErrorSimpleConversion(reverseResponse);

        // Assert
        Assert.assertEquals(error.getCod(), "" + HttpStatusCode.BAD_REQUEST.getStatusCode(), "message error - cod should be " + HttpStatusCode.BAD_REQUEST.getStatusCode());
        Assert.assertEquals(error.getMessage(), expected, "message error - message should be " + expected);
    }

    @Test
    public void testCaseNo3_byReverse_notExist() {
        /**
         * if no exist - returns empty
         */
        // Arrange
        String lat = "54";
        String lon = "1";

        // Action
        Response reverseResponse = HttpHelpers.MakeApiCall(UrlHelpers.getReverseEndPoint() + UrlHelpers.getReverseParams(lat, lon));
        Assert.assertEquals(reverseResponse.getStatusCode(), HttpStatusCode.OK.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.OK.getStatusCode());
        List<CoordinatesByName> coordinatesByNames = JsonHelpers.jsonObjectMapperConversionWithTry(reverseResponse);

        // Assert
        Assert.assertTrue(coordinatesByNames.isEmpty(), "message error - list of cities should be empty");
    }

    @Test
    public void testCaseNo3_byReverse_exist() {
        /**
         * if exist - returns just one
         */
        // Arrange
        String lat = "54";
        String lon = "0";
        String city = "England";
        String state = "England";
        String country = "GB";

        // Action
        Response reverseResponse = HttpHelpers.MakeApiCall(UrlHelpers.getReverseEndPoint() + UrlHelpers.getReverseParams(lat, lon));
        Assert.assertEquals(reverseResponse.getStatusCode(), HttpStatusCode.OK.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.OK.getStatusCode());
        List<CoordinatesByName> coordinatesByNames = JsonHelpers.jsonObjectMapperConversionWithTry(reverseResponse);

        // Assert
        Assert.assertFalse(coordinatesByNames.isEmpty(), "message error - list of cities should not be empty");
        Assert.assertTrue(coordinatesByNames.getFirst().getName().equalsIgnoreCase(city), "message error - name of city of first element should be " + city);
        Assert.assertTrue(coordinatesByNames.getFirst().getState().equalsIgnoreCase(state), "message error - state of city of first element should be " + state);
        Assert.assertTrue(coordinatesByNames.getFirst().getCountry().equalsIgnoreCase(country), "message error - country of city of first element should be " + country);
    }

    @Test
    public void testCaseNo3_byReverse_existWithLimit() {
        /**
         * if exist - returns just one
         */
        // Arrange
        String lat = "54";
        String lon = "0";
        int limit = 5;
        int expectedLimit = 1;
        String city = "England";
        String state = "England";
        String country = "GB";

        // Action
        Response reverseResponse = HttpHelpers.MakeApiCall(UrlHelpers.getReverseEndPoint() + UrlHelpers.getReverseParams(lat, lon, limit));
        Assert.assertEquals(reverseResponse.getStatusCode(), HttpStatusCode.OK.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.OK.getStatusCode());
        List<CoordinatesByName> coordinatesByNames = JsonHelpers.jsonObjectMapperConversionWithTry(reverseResponse);

        // Assert
        Assert.assertFalse(coordinatesByNames.isEmpty(), "message error - list of cities should not be empty");
        Assert.assertEquals(coordinatesByNames.size(), expectedLimit, "message error - size of list of cities should be " + expectedLimit);
        Assert.assertTrue(coordinatesByNames.getFirst().getName().equalsIgnoreCase(city), "message error - name of city of first element should be " + city);
        Assert.assertTrue(coordinatesByNames.getFirst().getState().equalsIgnoreCase(state), "message error - state of city of first element should be " + state);
        Assert.assertTrue(coordinatesByNames.getFirst().getCountry().equalsIgnoreCase(country), "message error - country of city of first element should be " + country);    }
}