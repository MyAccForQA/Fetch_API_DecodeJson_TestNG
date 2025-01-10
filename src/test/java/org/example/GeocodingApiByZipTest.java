package org.example;

import io.restassured.response.Response;
import org.example.helpers.*;
import org.example.responses.*;
import org.example.responses.Error;
import org.testng.Assert;
import org.testng.annotations.*;

public class GeocodingApiByZipTest {
    @BeforeMethod // before each test
    public void setUpBeforeMethod() {
        System.out.println("@BeforeMethod - to see in logs");
    }

    @AfterMethod // after each test
    public void setDownAfterMethod() {
        System.out.println("@AfterMethod - to see in logs");
    }

    @Test
    public void testCaseNo2_byZip_skip() {
        /**
         * if no zip - returns Nothing to geocode
         */
        // Arrange
        String zip = "";
        String expected = "Nothing to geocode";

        // Action
        Response zipResponse = HttpHelpers.MakeApiCall(UrlHelpers.getZipEndPoint() + UrlHelpers.getZipParams(zip), HttpMethod.GET, HttpStatusCode.BAD_REQUEST);
        Assert.assertEquals(zipResponse.getStatusCode(), HttpStatusCode.BAD_REQUEST.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.BAD_REQUEST.getStatusCode());
        Error error = JsonHelpers.jsonErrorSimpleConversion(zipResponse);

        // Assert
        Assert.assertEquals(error.getCod(), "" + HttpStatusCode.BAD_REQUEST.getStatusCode(), "message error - cod should be " + HttpStatusCode.BAD_REQUEST.getStatusCode());
        Assert.assertEquals(error.getMessage(), expected, "message error - message should be " + expected);
    }

    @Test
    public void testCaseNo2_byZip_Empty() {
        /**
         * if zip is empty - returns not found
         */
        // Arrange
        String zip = "''";
        String expected = "not found";

        // Action
        Response zipResponse = HttpHelpers.MakeApiCall(UrlHelpers.getZipEndPoint() + UrlHelpers.getZipParams(zip), HttpStatusCode.NOT_FOUND);
        Assert.assertEquals(zipResponse.getStatusCode(), HttpStatusCode.NOT_FOUND.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.NOT_FOUND.getStatusCode());
        Error error = JsonHelpers.jsonErrorSimpleConversion(zipResponse);

        // Assert
        Assert.assertEquals(error.getCod(), "" + HttpStatusCode.NOT_FOUND.getStatusCode(), "message error - cod should be " + HttpStatusCode.NOT_FOUND.getStatusCode());
        Assert.assertEquals(error.getMessage(), expected, "message error - message should be " + expected);
    }

    @Test
    public void testCaseNo2_byZip_zero() {
        /**
         * if zip is invalid (0) - returns invalid zip code
         */
        // Arrange
        String zip = "0";
        String expected = "invalid zip code";

        // Action
        Response zipResponse = HttpHelpers.MakeApiCall(UrlHelpers.getZipEndPoint() + UrlHelpers.getZipParams(zip), HttpStatusCode.BAD_REQUEST);
        Assert.assertEquals(zipResponse.getStatusCode(), HttpStatusCode.BAD_REQUEST.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.BAD_REQUEST.getStatusCode());
        Error error = JsonHelpers.jsonErrorSimpleConversion(zipResponse);

        // Assert
        Assert.assertEquals(error.getCod(), "" + HttpStatusCode.BAD_REQUEST.getStatusCode(), "message error - cod should be " + HttpStatusCode.BAD_REQUEST.getStatusCode());
        Assert.assertEquals(error.getMessage(), expected, "message error - message should be " + expected);
    }

    @Test
    public void testCaseNo2_byZip_1() {
        /**
         * if zip is invalid (1) - returns invalid zip code
         */
        // Arrange
        String zip = "1";
        String expected = "invalid zip code";

        // Action
        Response zipResponse = HttpHelpers.MakeApiCall(UrlHelpers.getZipEndPoint() + UrlHelpers.getZipParams(zip), HttpStatusCode.BAD_REQUEST);
        Assert.assertEquals(zipResponse.getStatusCode(), HttpStatusCode.BAD_REQUEST.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.BAD_REQUEST.getStatusCode());
        Error error = JsonHelpers.jsonErrorSimpleConversion(zipResponse);

        // Assert
        Assert.assertEquals(error.getCod(), "" + HttpStatusCode.BAD_REQUEST.getStatusCode(), "message error - cod should be " + HttpStatusCode.BAD_REQUEST.getStatusCode());
        Assert.assertEquals(error.getMessage(), expected, "message error - message should be " + expected);
    }

    @Test
    public void testCaseNo2_byZip_10() {
        /**
         * if zip is wrong (10) - returns not found
         */
        // Arrange
        String zip = "10";
        String expected = "not found";

        // Action
        Response zipResponse = HttpHelpers.MakeApiCall(UrlHelpers.getZipEndPoint() + UrlHelpers.getZipParams(zip), HttpStatusCode.NOT_FOUND);
        Assert.assertEquals(zipResponse.getStatusCode(), HttpStatusCode.NOT_FOUND.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.NOT_FOUND.getStatusCode());
        Error error = JsonHelpers.jsonErrorSimpleConversion(zipResponse);

        // Assert
        Assert.assertEquals(error.getCod(), "" + HttpStatusCode.NOT_FOUND.getStatusCode(), "message error - cod should be " + HttpStatusCode.NOT_FOUND.getStatusCode());
        Assert.assertEquals(error.getMessage(), expected, "message error - message should be " + expected);
    }

    public void testCaseNo2_byZip_mix() {
        /**
         * if zip is mix (1234f) - returns not found
         */
        // Arrange
        String zip = "1234f";
        String expected = "not found";

        // Action
        Response zipResponse = HttpHelpers.MakeApiCall(UrlHelpers.getZipEndPoint() + UrlHelpers.getZipParams(zip), HttpStatusCode.NOT_FOUND);
        Assert.assertEquals(zipResponse.getStatusCode(), HttpStatusCode.NOT_FOUND.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.NOT_FOUND.getStatusCode());
        Error error = JsonHelpers.jsonErrorSimpleConversion(zipResponse);

        // Assert
        Assert.assertEquals(error.getCod(), "" + HttpStatusCode.NOT_FOUND.getStatusCode(), "message error - cod should be " + HttpStatusCode.NOT_FOUND.getStatusCode());
        Assert.assertEquals(error.getMessage(), expected, "message error - message should be " + expected);
    }

    @Test
    public void testCaseNo2_byZip_12345() {
        /**
         * if zip is right (12345) - returns one
         */
        // Arrange
        String zip = "12345";
        String nameExpected = "schenectady";
        String countryExpected = "US";

        // Action
        Response zipResponse = HttpHelpers.MakeApiCall(UrlHelpers.getZipEndPoint() + UrlHelpers.getZipParams(zip));
        Assert.assertEquals(zipResponse.getStatusCode(), HttpStatusCode.OK.getStatusCode(), "message error - StatusCode should be " + HttpStatusCode.OK.getStatusCode());
        CoordinatesByZip coordinatesByZip = JsonHelpers.jsonSimpleConversion(zipResponse);

        // Assert
        Assert.assertEquals(coordinatesByZip.getZip(), zip, "message error - zip of citi should be " + zip);
        Assert.assertTrue(coordinatesByZip.getName().equalsIgnoreCase(nameExpected), "message error - name of city of first element should be " + nameExpected);
        Assert.assertTrue(coordinatesByZip.getCountry().equalsIgnoreCase(countryExpected), "message error - country of city should be " + countryExpected);
    }
}