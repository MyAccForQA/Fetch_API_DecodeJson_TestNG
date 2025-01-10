package org.example.helpers;

public class UrlHelpers {
    private final static String basicURL = "http://api.openweathermap.org/geo/1.0";
    private final static String nameEndPoint = "/direct";
    private final static String zipEndPoint = "/zip";
    private final static String reverseEndPoint = "/reverse";

    public static String getBasicURL() {
        return basicURL;
    }

    public static String getNameEndPoint() {
        return nameEndPoint;
    }

    public static String getNameParams(String name, String key) {
        return "?q=" + name + "&appid=" + key;
    }

    public static String getNameParams(String name) {
        return getNameParams(name, ApiKeyHelpers.getMyFirstKey());
    }

    public static String getNameParams(String name, int limit) {
        String params = getNameParams(name);
        if (limit != 0) {
            params += "&limit=" + limit;
        }
        return params;
    }

    public static String getZipEndPoint() {
        return zipEndPoint;
    }

    public static String getZipParams(String zip) {
        return "?zip=" + zip + "&appid=" + ApiKeyHelpers.getMyFirstKey();
    }

    public static String getReverseEndPoint() {
        return reverseEndPoint;
    }

    public static String getReverseParams(String lat, String lon) {
        return "?lat=" + lat + "&lon=" + lon + "&appid=" + ApiKeyHelpers.getMyFirstKey();
    }

    public static String getReverseParams(String lat, String lon, int limit) {
        String params = getReverseParams(lat, lon);
        if (limit != 0) {
            params += "&limit=" + limit;
        }
        return params;
    }
}