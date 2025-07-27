package testcases;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDate;

import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import pojo.Login;
import routes.Routes;
import utils.ConfigReader;

import static io.restassured.RestAssured.given;

public class Baseclass {

	protected static String token;  // Shared by all tests
	ConfigReader configReader;

	// Logging filters
	RequestLoggingFilter requestLoggingFilter;
	ResponseLoggingFilter responseLoggingFilter;

	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@BeforeClass(alwaysRun = true)
	public void setup() throws FileNotFoundException {
		RestAssured.baseURI = Routes.BASE_URL;

		configReader = new ConfigReader();

		// Setup logging to a file
		FileOutputStream fos = new FileOutputStream(".\\logs\\test_logging.log");
		PrintStream log = new PrintStream(fos, true);

		requestLoggingFilter = new RequestLoggingFilter(log);
		responseLoggingFilter = new ResponseLoggingFilter(log);

		RestAssured.filters(requestLoggingFilter, responseLoggingFilter);

		// Generate token only once and reuse
		if (token == null || token.isEmpty()) {
			generateToken();
		}
	}

	private void generateToken() {
	    String username = configReader.getProperty("username");
	    String password = configReader.getProperty("password");

	    // ✅ Null check to avoid creating a broken Login object
	    if (username == null || password == null) {
	        throw new RuntimeException("Username or Password is null. Check your config.properties file.");
	    }

	    Login loginPayload = new Login(username, password);

	    Response response = given()
	            .header("Content-Type", "application/json")
	            .body(loginPayload)
	            .post(Routes.AUTH_LOGIN);

	    if (response.getStatusCode() == 200) {
	        token= response.jsonPath().getString("token");
	        System.out.println("Token generated: " + token);
	    } else {
	        throw new RuntimeException("Login failed. Status code: " + response.getStatusCode());
	    }
	}
	// Optional helper to get token (can be used if needed elsewhere)
	public String getToken() {
		return token;
	}

	// ----------------- Helper methods ------------------

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
