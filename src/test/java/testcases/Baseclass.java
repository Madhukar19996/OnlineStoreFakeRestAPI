package testcases;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
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

	protected static String token; // Shared across tests
	protected static ConfigReader configReader;
	protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private static RequestLoggingFilter requestLoggingFilter;
	private static ResponseLoggingFilter responseLoggingFilter;

	@BeforeSuite(alwaysRun = true)
	public void initializeSuite() {
		RestAssured.baseURI = Routes.BASE_URL;

		configReader = new ConfigReader();

		// Logging setup
		try {
			FileOutputStream fos = new FileOutputStream(".\\logs\\test_logging.log");
			PrintStream log = new PrintStream(fos, true);
			requestLoggingFilter = new RequestLoggingFilter(log);
			responseLoggingFilter = new ResponseLoggingFilter(log);
			RestAssured.filters(requestLoggingFilter, responseLoggingFilter);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Failed to initialize logging", e);
		}

		// Generate token only once
		if (token == null || token.isEmpty()) {
			token = generateToken();
		}
	}

	@BeforeClass(alwaysRun = true)
	public void setupClassLevel() {
		// If token somehow wasn't initialized
		if (token == null || token.isEmpty()) {
			token = generateToken();
		}
	}

	protected String generateToken() {
		String username = configReader.getProperty("username");
		String password = configReader.getProperty("password");

		if (username == null || password == null) {
			throw new RuntimeException("Username or Password not found. Please check config.properties.");
		}

		Login loginPayload = new Login(username, password);

		Response response = given()
				.header("Content-Type", "application/json")
				.body(loginPayload)
				.post(Routes.AUTH_LOGIN);

		if (response.getStatusCode() == 200) {
			String generatedToken = response.jsonPath().getString("token");
			System.out.println("Token generated successfully. :"+generatedToken);
			return generatedToken;
		} else {
			throw new RuntimeException("Login failed. Status Code: " + response.getStatusCode());
		}
	}

	// Helper to access token if needed externally
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
