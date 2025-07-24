package testcases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.Payload;
import pojo.User;
import routes.Routes;

public class UserTests extends Baseclass {


	//1) Fetch all the users 
	@Test()
	public void testGetAllUsers()
	{
		given()

		.when()
		.get(Routes.GET_ALL_USERS)
		.then()
		.statusCode(200)
		.contentType(ContentType.JSON)
		.log().body()
		.body("size()",greaterThan(0));
	}


	//2) Test to fetch a specific User by Id
	@Test()
	public void testGetUserById()
	{

		int userId=configReader.getIntProperty("userId");
		given()
		.pathParam("id",userId)

		.when()
		.get(Routes.GET_USER_BY_ID)
		.then()
		.log().body()
		.statusCode(200);

	}


	//3) Test to fetch a limited number of users
	@Test()
	public void testGetUserswithLimit()
	{

		int limit=configReader.getIntProperty("limit");
		given()
		.pathParam("limit",limit)

		.when()
		.get(Routes.GET_USERS_WITH_LIMIT)
		.then()
		.log().body()
		.body("size()",equalTo(3))
		.statusCode(200);

	}


	//4) Test to fetch users sorted in descending order
	@Test()
	public void  testGetUsersSorted()
	{


		Response response=given()
				.pathParam("order","desc")

				.when()
				.get(Routes.GET_USERS_SORTED)
				.then()
				.statusCode(200)
				.log().body()
				.extract().response();

		List<Integer> usersIds=response.jsonPath().getList("id",Integer.class);

		assertThat(isSortedDescending(usersIds),is(true));



	}

	//5) Test to fetch users sorted in ascending order
	@Test()
	public void  testGetUsersSortedAsc()
	{


		Response response=given()
				.pathParam("order","asc")

				.when()
				.get(Routes.GET_USERS_SORTED)
				.then()
				.statusCode(200)
				.log().body()
				.extract().response();

		List<Integer> usersIds=response.jsonPath().getList("id",Integer.class);

		assertThat(isSortedAscending(usersIds),is(true));



	}



	//6) Test to create new user
	@Test()
	public void  testCreateUser()
	{

		User newUser=Payload.userPayload();
		int id=given()
				.contentType(ContentType.JSON)
				.body(newUser)

				.when()
				.post(Routes.CREATE_USER)
				.then()
				.statusCode(200)
				.log().body()
				.body("id", notNullValue())
				.extract().jsonPath().getInt("id");
		System.out.println("Generated UserId==:"+ id);


	}


	//7) Test to update user
	@Test()
	public void  testUpdateUser()
	{   

		int userId=configReader.getIntProperty("userId");
		User updateuser=Payload.userPayload(); 

		given()
		.contentType(ContentType.JSON)
		.pathParam("id",userId)
		.body(updateuser)

		.when()
		.put(Routes.UPDATE_USER)
		.then()
		.statusCode(200)
		.log().body()
		.body("username",equalTo(updateuser.getUsername()));

	}


	//8) delete user

	@Test
	void testDeleteUser()
	{

		int userId=configReader.getIntProperty("userId");

		given()
		.pathParam("id", userId)
		.when()
		.delete(Routes.DELETE_USER)
		.then()
		.statusCode(200);
	}


}
