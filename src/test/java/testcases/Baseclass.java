package testcases;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import pojo.Login;
import routes.Routes;
import utils.ConfigReader;

import static io.restassured.RestAssured.given;

public class Baseclass {

    protected static String token;
    protected static ConfigReader configReader;
    protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static RequestLoggingFilter requestLoggingFilter;
    private static ResponseLoggingFilter responseLoggingFilter;

    @BeforeSuite(alwaysRun = true)
    public void initializeSuite() {
        try {
            configReader = new ConfigReader();

            String baseUrl = Routes.BASE_URL;
            if (baseUrl == null || baseUrl.isEmpty()) {
                throw new RuntimeException("BASE_URL is not configured properly in Routes class.");
            }

            RestAssured.baseURI = baseUrl;

            initLogging();

            if (token == null || token.isEmpty()) {
                token = generateToken();
            }

        } catch (Exception e) {
            System.err.println("Error during suite initialization: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Suite initialization failed due to: " + e.getMessage());
        }
    }

    @BeforeClass(alwaysRun = true)
    public void setupClassLevel() {
        if (token == null || token.isEmpty()) {
            token = generateToken();
        }
    }

    protected void initLogging() throws FileNotFoundException {
        String logDirPath = "logs";
        File logDir = new File(logDirPath);
        if (!logDir.exists()) {
            logDir.mkdir(); // create directory if missing
        }

        FileOutputStream fos = new FileOutputStream(logDirPath + "/test_logging.log");
        PrintStream log = new PrintStream(fos, true);
        requestLoggingFilter = new RequestLoggingFilter(log);
        responseLoggingFilter = new ResponseLoggingFilter(log);
        RestAssured.filters(requestLoggingFilter, responseLoggingFilter);
    }

    protected String generateToken() {
        try {
            String username = configReader.getProperty("username");
            String password = configReader.getProperty("password");

            if (username == null || password == null) {
                throw new RuntimeException("Username or Password is null. Check config.properties.");
            }

            Login loginPayload = new Login(username, password);
            String loginUrl = Routes.AUTH_LOGIN;

            if (loginUrl == null || loginUrl.isEmpty()) {
                throw new RuntimeException("AUTH_LOGIN route is not defined.");
            }

            Response response = given()
                    .header("Content-Type", "application/json")
                    .body(loginPayload)
                    .post(loginUrl);

            if (response.getStatusCode() == 200) {
                String generatedToken = response.jsonPath().getString("token");
                System.out.println("Token generated successfully:"+generatedToken);
                return generatedToken;
            } else {
                System.err.println("❌ Login failed. Status: " + response.getStatusCode() + ", Body: " + response.asString());
                throw new RuntimeException("Login failed. Status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error during token generation: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Token generation failed.");
        }
    }

    public String getToken() {
        return token;
    }

    // ------------------- Utility Methods -------------------

    public boolean isSortedDescending(List<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) < list.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public boolean isSortedAscending(List<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) > list.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public boolean validateCartDatesWithinRange(List<String> cartDates, String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate, FORMATTER);
        LocalDate end = LocalDate.parse(endDate, FORMATTER);

        for (String dateTime : cartDates) {
            LocalDate cartDate = LocalDate.parse(dateTime.substring(0, 10), FORMATTER);
            if (cartDate.isBefore(start) || cartDate.isAfter(end)) {
                return false;
            }
        }
        return true;
    }
}
