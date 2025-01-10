package org.example.apps;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.example.helpers.*;
import org.example.responses.*;
import java.util.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class RunApp {
    private Scanner scanner;

    public RunApp() {
        scanner = new Scanner(System.in);
        int choice;
        do {
            displayMenu();
            choice = getIntegerInput();
            switch (choice) {
                case 1: // name
                    caseNo1_nameRun();
                    break;
                case 2: // zip
                    caseNo2_zipRun();
                    break;
                case 3: // reverse
                    caseNo3_reverseRun();
                    break;
                case 4: // multiple
                    runCaseNo4_multiple();
                    break;
                case 0: // exit
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 0);
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\n\n\nChoose from these choices");
        System.out.println("-------------------------");
        System.out.println("1. Coordinates by location name");
        System.out.println("2. Coordinates by zip/post code");
        System.out.println("3. Reverse geocoding");
        System.out.println("4. Multiple locations");
        System.out.println("0. Quit");
    }

    private int getIntegerInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("\nInvalid input. Please enter a number.");
            scanner.next(); // Consume non-integer input
        }
        return scanner.nextInt();
    }

    private String caseNo1_nameParams() {
        // clean
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        System.out.println("\nExecuting option 1. Coordinates by location name. Enter location name: ");
        String name = scanner.nextLine().trim();
        System.out.println("\nPlease enter a number to use limit (0 - no limit): ");
        int limit = getIntegerInput();
        return UrlHelpers.getNameParams(name, limit);
    }

    private List<CoordinatesByName> caseNo1_nameConvert(Response nameResponse) {
        List<CoordinatesByName> coordinatesByNames;
        try {
            coordinatesByNames = JsonHelpers.jsonObjectMapperConversion(nameResponse);
        } catch (JsonProcessingException e) {
            // throw new RuntimeException(e);
            System.out.println("\nNOT able to convert response to JSON!!!");
            return null;
        }
        return coordinatesByNames;
    }

    private void caseNo1_namePrint(List<CoordinatesByName> coordinatesByNames) {
        // get the first element of the array
        if ((coordinatesByNames == null) || (coordinatesByNames.isEmpty())) {
            System.out.println("\nNOT found!!!");
            return;
        }
        CoordinatesByName coordinatesByName = coordinatesByNames.getFirst();
        // print result
        System.out.println("\nPrint response as CoordinatesByName.class of the first element:");
        System.out.println("name: " + coordinatesByName.getName());
        System.out.println("local_names: " + coordinatesByName.getLocalNames());
        String key = "ascii";
        System.out.println("local_names[key]: " + coordinatesByName.getLocalNameByKey(key));
        System.out.println("lat: " + coordinatesByName.getLat());
        System.out.println("lon: " + coordinatesByName.getLon());
        System.out.println("country: " + coordinatesByName.getCountry());
        System.out.println("state: " + coordinatesByName.getState());
        // OR
        // coordinatesByName.forEach(System.out::println);
    }

    private void caseNo1_nameRun() {
        String nameParams = caseNo1_nameParams();
        Response nameResponse = HttpHelpers.MakeApiCall(UrlHelpers.getNameEndPoint() + nameParams);
        List<CoordinatesByName> coordinatesByNames = caseNo1_nameConvert(nameResponse);
        caseNo1_namePrint(coordinatesByNames);
    }

    private String caseNo2_zipParams() {
        // clean
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        System.out.println("\nExecuting option 2. Coordinates by zip/post code [12345, 1234f, asd3f, 1asdf]. Enter zip/post code: ");
        String zip = "";
        String param = "";
        while (true) {
           zip = scanner.next().trim(); // read before space
           if (zip.matches(".....") && (Pattern.compile(".*\\d.*").matcher(zip).matches())) {
               param = UrlHelpers.getZipParams(zip);
               break;
           } else {
               System.out.println("\nInvalid input. Please enter right format of zipcode [12345, 1234f, asd3f, 1asdf].");
           }
        }
        return param;
    }

    private CoordinatesByZip caseNo2_zipConvert(Response zipResponse) {
        if (zipResponse.getStatusCode() == HttpStatusCode.BAD_REQUEST.getStatusCode()) {
            System.out.println("\nNOT found - since got error 'invalid zip code'!!!");
            return null;
        } else if (zipResponse.getStatusCode() == HttpStatusCode.NOT_FOUND.getStatusCode()) {
            System.out.println("\nNOT found - since got error 'not found'!!!");
            return null;
        }
        return JsonHelpers.jsonSimpleConversion(zipResponse);
    }

    private void caseNo2_zipPrint(CoordinatesByZip coordinatesByZip) {
        if (coordinatesByZip == null) {
            return;
        }
        // print result
        System.out.println("\nPrint response as CoordinatesByZip.class:");
        System.out.println("zip: " + coordinatesByZip.getZip());
        System.out.println("name: " + coordinatesByZip.getName());
        System.out.println("lat: " + coordinatesByZip.getLat());
        System.out.println("lon: " + coordinatesByZip.getLon());
        System.out.println("country: " + coordinatesByZip.getCountry());
    }

    private void caseNo2_zipRun() {
        String zipParams = caseNo2_zipParams();
        Response zipResponse = HttpHelpers.MakeApiCall(UrlHelpers.getZipEndPoint() + zipParams);
        CoordinatesByZip coordinatesByZip = caseNo2_zipConvert(zipResponse);
        caseNo2_zipPrint(coordinatesByZip);
    }

    private String caseNo3_reverseParams() {
        // clean
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        System.out.println("\nExecuting option 3. Reverse - get name by geografical. Enter lat code: ");
        String lat = scanner.next();
        System.out.println("\nEnter lon code: ");
        String lon = scanner.next();
        System.out.println("\nPlease enter a number to use limit (0 - no limit): ");
        int limit = getIntegerInput();
        return UrlHelpers.getReverseParams(lat, lon, limit);
    }

    private void caseNo3_reverseRun() {
        String reverseParams = caseNo3_reverseParams();
        Response reverseResponse = HttpHelpers.MakeApiCall(UrlHelpers.getReverseEndPoint() + reverseParams);
        // use 'CoordinatesByName.class' since structure is same
        List<CoordinatesByName> coordinatesByNames = caseNo1_nameConvert(reverseResponse);
        caseNo1_namePrint(coordinatesByNames);
        // OR to print
        // coordinatesByName.forEach(System.out::println);
        /* // just by simple JSON objects - more in JsonHelpers.jsonAsExampleWithArray
        System.out.println("\nPrint response as class of the first element through JSONObject:");
        JSONObject json = new JSONObject(reverseResponse.asString());
        JSONArray items = json.getJSONArray("results");
        System.out.println(STR."items.size() - \{items.length()}");
        JSONObject item0 = items.getJSONObject(0);
        System.out.println(STR."name - \{item0.get("name")}");
        System.out.println("OR");
        String name = reverseResponse.then().contentType(ContentType.JSON).extract().path("results[0].name");
        System.out.println(STR."name - \{name}"); */
    }

    private List<String> option4_multipleParams() {
        System.out.println("\nExecuting option 4. Multiple locations. Enter locations (city, state, zipcode[1234f, asd3f, 1asdf]) or done for finish: ");
        List<String> params = new ArrayList<>();
        int count = 1;

        // clean
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (true) {
            System.out.print("Enter location #" + count + ": ");
            String input = scanner.nextLine().trim();
            if ("done".equalsIgnoreCase(input)) {
                break;
            }
            if (!input.isEmpty()) {
                params.add(input);
                count++;
            } else {
                System.out.println("Location can not be empty. Try one more time or use done for finish.");
            }
        }
        return params;
    }

    private void runCaseNo4_multiple() {
        List<String> inputs = option4_multipleParams();
        if (inputs.isEmpty()) {
            System.out.println("You have not entered any location.");
            return;
        }
        System.out.println("\nYou entered locations: " + inputs);
        for (String input : inputs) {
            try {
                // simple check if input is ZIP code (12345 or 1234F or asd4r)
                if (input.matches(".....") && (Pattern.compile(".*\\d.*").matcher(input).matches())) {
                    System.out.println("\nPrint location by zipcode: " + input);
                    Response zipResponse = HttpHelpers.MakeApiCall(UrlHelpers.getZipEndPoint() + UrlHelpers.getZipParams(input), (input.matches("\\d{5}") ? HttpStatusCode.OK : HttpStatusCode.NOT_FOUND));
                    CoordinatesByZip coordinatesByZip = caseNo2_zipConvert(zipResponse);
                    caseNo2_zipPrint(coordinatesByZip);
                } else {
                    System.out.println("\nPrint location by name: " + input);
                    Response nameResponse = HttpHelpers.MakeApiCall(UrlHelpers.getNameEndPoint() + UrlHelpers.getNameParams(input));
                    List<CoordinatesByName> coordinatesByNames = caseNo1_nameConvert(nameResponse);
                    caseNo1_namePrint(coordinatesByNames);
                }
            } catch (Exception e) {
                System.err.println("Failed to fetch location for: " + input + ". Error: " + e.getMessage());
            }
        }
    }
}