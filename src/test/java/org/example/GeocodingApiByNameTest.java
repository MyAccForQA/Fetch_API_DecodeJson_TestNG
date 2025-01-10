package org.example;

import io.restassured.response.Response;
import org.example.helpers.*;
import org.example.responses.*;
import org.example.responses.Error;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.List;

public class GeocodingApiByNameTest {
    @BeforeMethod // before each test
    public void setUpBeforeMethod() {
        System.out.println("@BeforeMethod - to see in logs");
    }

    @AfterMethod // after each test
    public void setDownAfterMethod() {
        System.out.println("@AfterMethod - to see in logs");
    }

    @Test
    public void testCaseNo1_byName_noKey() {
        /**
         * if no key - returns Invalid API key. Please see https://openweathermap.org/faq#error401 for more info.
         */
        // Arrange
        String city = "london";
        String key = "";
        String expected = "Invalid API key. Please see https://openweathermap.org/faq#error401 for more info.";

        // Action
        Response nameResponse = HttpHelpers.MakeApiCall(UrlHelpers.getNameEndPoint() + UrlHelpers.getNameParams(city, key), HttpMethod.GET, HttpStatusCode.UNAUTHORIZED);
        Assert.assertEquals(nameResponse.getStatusCode(), HttpStatusCode.UNAUTHORIZED.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.UNAUTHORIZED.getStatusCode());
        Error error = JsonHelpers.jsonErrorSimpleConversion(nameResponse);

        // Assert
        Assert.assertEquals(error.getCod(), "" + HttpStatusCode.UNAUTHORIZED.getStatusCode(), "message error - cod should be " + HttpStatusCode.UNAUTHORIZED.getStatusCode());
        Assert.assertEquals(error.getMessage(), expected, "message error - message should be " + expected);
    }

    @Test
    public void testCaseNo1_byName_skip() {
        /**
         * if no name - returns Nothing to geocode
         */
        // Arrange
        String city = "";
        String expected = "Nothing to geocode";

        // Action
        Response nameResponse = HttpHelpers.MakeApiCall(UrlHelpers.getNameEndPoint() + UrlHelpers.getNameParams(city), HttpMethod.GET, HttpStatusCode.BAD_REQUEST);
        Assert.assertEquals(nameResponse.getStatusCode(), HttpStatusCode.BAD_REQUEST.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.BAD_REQUEST.getStatusCode());
        Error error = JsonHelpers.jsonErrorSimpleConversion(nameResponse);

        // Assert
        Assert.assertEquals(error.getCod(), "" + HttpStatusCode.BAD_REQUEST.getStatusCode(), "message error - cod should be " + HttpStatusCode.BAD_REQUEST.getStatusCode());
        Assert.assertEquals(error.getMessage(), expected, "message error - message should be " + expected);
    }

    @Test
    public void testCaseNo2_byZip_Empty() {
        /**
         * if name is empty - returns empty
         */
        // Arrange
        String city = "''";

        // Action
        Response nameResponse = HttpHelpers.MakeApiCall(UrlHelpers.getNameEndPoint() + UrlHelpers.getNameParams(city));
        Assert.assertEquals(nameResponse.getStatusCode(), HttpStatusCode.OK.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.OK.getStatusCode());
        List<CoordinatesByName> coordinatesByNames = JsonHelpers.jsonObjectMapperConversionWithTry(nameResponse);

        // Assert
        Assert.assertTrue(coordinatesByNames.isEmpty(), "message error - list of cities should be empty");
    }

    @Test
    public void testCaseNo1_byName_londonWithNoLimit() {
        /**
         * if no limit - returns just one
         */
        // Arrange
        String city = "london";
        String state = "England";
        String country = "GB";
        int expected = 1;

        // Action
        Response nameResponse = HttpHelpers.MakeApiCall(UrlHelpers.getNameEndPoint() + UrlHelpers.getNameParams(city), HttpMethod.GET);
        Assert.assertEquals(nameResponse.getStatusCode(), HttpStatusCode.OK.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.OK.getStatusCode());
        List<CoordinatesByName> coordinatesByNames = JsonHelpers.jsonObjectMapperConversionWithTry(nameResponse);

        // Assert
        Assert.assertFalse(coordinatesByNames.isEmpty(), "message error - list of cities should not be empty");
        Assert.assertEquals(coordinatesByNames.size(), expected, "message error - size of list of cities should be " + expected);
        Assert.assertTrue(coordinatesByNames.getFirst().getName().equalsIgnoreCase(city), "message error - name of city of first element should be " + city);
        Assert.assertTrue(coordinatesByNames.getFirst().getState().equalsIgnoreCase(state), "message error - state of city of first element should be " + state);
        Assert.assertTrue(coordinatesByNames.getFirst().getCountry().equalsIgnoreCase(country), "message error - country of city of first element should be " + country);
    }

    @Test
    public void testCaseNo1_byName_londonWithLimit2() {
        /**
         * if limit - returns by limit
         */
        // Arrange
        String city = "london";
        String state = "England";
        String country = "GB";
        int limit = 2;

        // Action
        Response nameResponse = HttpHelpers.MakeApiCall(UrlHelpers.getNameEndPoint() + UrlHelpers.getNameParams(city, limit));
        Assert.assertEquals(nameResponse.getStatusCode(), HttpStatusCode.OK.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.OK.getStatusCode());
        List<CoordinatesByName> coordinatesByNames = JsonHelpers.jsonObjectMapperConversionWithTry(nameResponse);

        // Assert
        Assert.assertFalse(coordinatesByNames.isEmpty(), "message error - list of cities should not be empty");
        Assert.assertEquals(coordinatesByNames.size(), limit, "message error - size of list of cities should be " + limit);
        Assert.assertTrue(coordinatesByNames.getFirst().getName().equalsIgnoreCase(city), "message error - name of city of first element should be " + city);
        Assert.assertTrue(coordinatesByNames.getFirst().getState().equalsIgnoreCase(state), "message error - state of city of first element should be " + state);
        Assert.assertTrue(coordinatesByNames.getFirst().getCountry().equalsIgnoreCase(country), "message error - country of city of first element should be " + country);
    }

    @Test
    public void testCaseNo1_byName_londonWithLimit5() {
        /**
         * if limit - returns by limit
         */
        // Arrange
        String city = "london";
        String state = "England";
        String country = "GB";
        int limit = 5;

        // Action
        Response nameResponse = HttpHelpers.MakeApiCall(UrlHelpers.getNameEndPoint() + UrlHelpers.getNameParams(city, limit));
        Assert.assertEquals(nameResponse.getStatusCode(), HttpStatusCode.OK.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.OK.getStatusCode());
        List<CoordinatesByName> coordinatesByNames = JsonHelpers.jsonObjectMapperConversionWithTry(nameResponse);

        // Assert
        Assert.assertFalse(coordinatesByNames.isEmpty(), "message error - list of cities should not be empty");
        Assert.assertEquals(coordinatesByNames.size(), limit, "message error - size of list of cities should be " + limit);
        Assert.assertTrue(coordinatesByNames.getFirst().getName().equalsIgnoreCase(city), "message error - name of city of first element should be " + city);
        Assert.assertTrue(coordinatesByNames.getFirst().getState().equalsIgnoreCase(state), "message error - state of city of first element should be " + state);
        Assert.assertTrue(coordinatesByNames.getFirst().getCountry().equalsIgnoreCase(country), "message error - country of city of first element should be " + country);
    }

    @Test
    public void testCaseNo1_byName_londonWithLimit10() {
        /**
         * if limit more than count - returns by count
         */
        // Arrange
        String city = "london";
        String state = "England";
        String country = "GB";
        int limit = 10;
        int expected = 5;

        // Action
        Response nameResponse = HttpHelpers.MakeApiCall(UrlHelpers.getNameEndPoint() + UrlHelpers.getNameParams(city, limit));
        Assert.assertEquals(nameResponse.getStatusCode(), HttpStatusCode.OK.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.OK.getStatusCode());
        List<CoordinatesByName> coordinatesByNames = JsonHelpers.jsonObjectMapperConversionWithTry(nameResponse);

        // Assert
        Assert.assertFalse(coordinatesByNames.isEmpty(), "message error - list of cities should not be empty");
        Assert.assertEquals(coordinatesByNames.size(), expected, "message error - size of list of cities should be " + expected);
        Assert.assertTrue(coordinatesByNames.getFirst().getName().equalsIgnoreCase(city), "message error - name of city of first element should be " + city);
        Assert.assertTrue(coordinatesByNames.getFirst().getState().equalsIgnoreCase(state), "message error - state of city of first element should be " + state);
        Assert.assertTrue(coordinatesByNames.getFirst().getCountry().equalsIgnoreCase(country), "message error - country of city of first element should be " + country);
    }

    @Test
    public void testCaseNo1_byName_cityWithNoState() {
        /**
         * if exist - returns just one
         */
        // Arrange
        String city = "parrish";
        String state = "Alabama";
        String country = "US";

        // Action
        Response nameResponse = HttpHelpers.MakeApiCall(UrlHelpers.getNameEndPoint() + UrlHelpers.getNameParams(city));
        Assert.assertEquals(nameResponse.getStatusCode(), HttpStatusCode.OK.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.OK.getStatusCode());
        List<CoordinatesByName> coordinatesByNames = JsonHelpers.jsonObjectMapperConversionWithTry(nameResponse);

        // Assert
        Assert.assertFalse(coordinatesByNames.isEmpty(), "message error - list of cities should not be empty");
        Assert.assertTrue(coordinatesByNames.getFirst().getName().equalsIgnoreCase(city), "message error - name of city of first element should be " + city);
        Assert.assertTrue(coordinatesByNames.getFirst().getState().equalsIgnoreCase(state), "message error - state of city of first element should be " + state);
        Assert.assertTrue(coordinatesByNames.getFirst().getCountry().equalsIgnoreCase(country), "message error - country of city of first element should be " + country);
    }

    @Test
    public void testCaseNo1_byName_cityWithState_andNoSpase() {
        /**
         * if exist city and state but with no spase - returns just one
         */
        // Arrange
        String city = "San Francisco";
        String state = "California";
        String country = "US";
        String nameForSearch = city + "," + state; // result = "San Francisco,California"

        // Action
        Response nameResponse = HttpHelpers.MakeApiCall(UrlHelpers.getNameEndPoint() + UrlHelpers.getNameParams(nameForSearch));
        Assert.assertEquals(nameResponse.getStatusCode(), HttpStatusCode.OK.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.OK.getStatusCode());
        List<CoordinatesByName> coordinatesByNames = JsonHelpers.jsonObjectMapperConversionWithTry(nameResponse);

        // Assert
        Assert.assertFalse(coordinatesByNames.isEmpty(), "message error - list of cities should not be empty");
        Assert.assertTrue(coordinatesByNames.getFirst().getName().equalsIgnoreCase(city), "message error - name of city of first element should be " + city);
        Assert.assertTrue(coordinatesByNames.getFirst().getState().equalsIgnoreCase(state), "message error - state of city of first element should be " + state);
        Assert.assertTrue(coordinatesByNames.getFirst().getCountry().equalsIgnoreCase(country), "message error - country of city of first element should be " + country);
    }

    @Test
    public void testCaseNo1_byName_cityWithState_andSpase() {
        /**
         * if exist city and state but with spase - returns just one
         */
        // Arrange
        String city = "San Francisco";
        String state = "California";
        String country = "US";
        String nameForSearch = city + "," + state; // result = "San Francisco, California"

        // Action
        Response nameResponse = HttpHelpers.MakeApiCall(UrlHelpers.getNameEndPoint() + UrlHelpers.getNameParams(nameForSearch));
        Assert.assertEquals(nameResponse.getStatusCode(), HttpStatusCode.OK.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.OK.getStatusCode());
        List<CoordinatesByName> coordinatesByNames = JsonHelpers.jsonObjectMapperConversionWithTry(nameResponse);

        // Assert
        Assert.assertFalse(coordinatesByNames.isEmpty(), "message error - list of cities should not be empty");
        Assert.assertTrue(coordinatesByNames.getFirst().getName().equalsIgnoreCase(city), "message error - name of city of first element should be " + city);
        Assert.assertTrue(coordinatesByNames.getFirst().getState().equalsIgnoreCase(state), "message error - state of city of first element should be " + state);
        Assert.assertTrue(coordinatesByNames.getFirst().getCountry().equalsIgnoreCase(country), "message error - country of city of first element should be " + country);
    }

    @Test
    public void testCaseNo1_byName_cityWithStateAsPartOfOtherCity_withNoСomma() {
        /**
         * if exist city and state but with no comma - returns empty
         */
        // Arrange
        String city = "San Francisco California";
        String cityExpected= "Finca San Francisco de California";
        String state = "Zamora Chinchipe";
        String country = "EC";

        // Action
        Response nameResponse = HttpHelpers.MakeApiCall(UrlHelpers.getNameEndPoint() + UrlHelpers.getNameParams(city));
        Assert.assertEquals(nameResponse.getStatusCode(), HttpStatusCode.OK.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.OK.getStatusCode());
        List<CoordinatesByName> coordinatesByNames = JsonHelpers.jsonObjectMapperConversionWithTry(nameResponse);

        // Assert
        Assert.assertFalse(coordinatesByNames.isEmpty(), "message error - list of cities should not be empty");
        Assert.assertTrue(coordinatesByNames.getFirst().getName().equalsIgnoreCase(cityExpected), "message error - name of city of first element should be " + cityExpected);
        Assert.assertTrue(coordinatesByNames.getFirst().getState().equalsIgnoreCase(state), "message error - state of city of first element should be " + state);
        Assert.assertTrue(coordinatesByNames.getFirst().getCountry().equalsIgnoreCase(country), "message error - country of city of first element should be " + country);
    }

    @Test
    public void testCaseNo1_byName_parrisih() {
        /**
         * if no exist - returns empty
         */
        // Arrange
        String city = "parrisih";

        // Action
        Response nameResponse = HttpHelpers.MakeApiCall(UrlHelpers.getNameEndPoint() + UrlHelpers.getNameParams(city));
        Assert.assertEquals(nameResponse.getStatusCode(), HttpStatusCode.OK.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.OK.getStatusCode());
        List<CoordinatesByName> coordinatesByNames = JsonHelpers.jsonObjectMapperConversionWithTry(nameResponse);

        // Assert
        Assert.assertTrue(coordinatesByNames.isEmpty(), "message error - list of cities should be empty");
    }

    @Test
    public void testCaseNo1_byName_asPart() {
        /**
         * if part of name - returns just one
         */
        // Arrange
        String city = "asdf";
        String cityExpected = "Oberasdorf";
        String state = "Rhineland-Palatinate";
        String country = "DE";

        // Action
        Response nameResponse = HttpHelpers.MakeApiCall(UrlHelpers.getNameEndPoint() + UrlHelpers.getNameParams(city));
        Assert.assertEquals(nameResponse.getStatusCode(), HttpStatusCode.OK.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.OK.getStatusCode());
        List<CoordinatesByName> coordinatesByNames = JsonHelpers.jsonObjectMapperConversionWithTry(nameResponse);

        // Assert
        Assert.assertFalse(coordinatesByNames.isEmpty(), "message error - list of cities should not be empty");
        Assert.assertTrue(coordinatesByNames.getFirst().getName().equalsIgnoreCase(cityExpected), "message error - name of city of first element should be " + cityExpected);
        Assert.assertTrue(coordinatesByNames.getFirst().getState().equalsIgnoreCase(state), "message error - state of city of first element should be " + state);
        Assert.assertTrue(coordinatesByNames.getFirst().getCountry().equalsIgnoreCase(country), "message error - country of city of first element should be " + country);
    }
}