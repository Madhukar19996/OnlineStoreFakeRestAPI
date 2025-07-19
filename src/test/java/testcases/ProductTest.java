package testcases;

import pojo.Product;
import routes.Routes;
import utils.ConfigReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.Payload;

import org.testng.ITestContext;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.List;

public class ProductTest extends Baseclass {


	//1) Test to retrieve all products
	@Test()
	public void testGetAllProducts()
	{
		given()

		.when()
		.get(Routes.GET_ALL_PRODUCTS)

		.then()
		.statusCode(200)
		//.log().body()
		.body("size()",greaterThan(0));

	} 


	//2)Test to retrieve a single product by ID 

	@Test()
	public void testGetProductByID()
	{   
		int productId =configReader.getIntProperty("productId");

		given()
		.pathParam("id",productId)

		.when()
		.get(Routes.GET_PRODUCT_BY_ID)

		.then()
		.statusCode(200);
		//.log().body();
	}



	//3)Test to retrieve a limited number of products

	@Test()
	public void testGetLimitedProducts()
	{   


		given()
		.pathParam("limit", 5)

		.when()
		.get(Routes.GET_PRODUCTS_WITH_LIMIT)

		.then()
		.statusCode(200)
		//.log().body()
		.body("size()",equalTo(5));

	}


	//4)Test to retrieve products sorted in descending order

	@Test()
	public void testGetSortedProducts()
	{   


		Response response=given()
				.pathParam("order", "desc")


				.when()
				.get(Routes.GET_PRODUCTS_SORTED)

				.then()
				.statusCode(200)
				//.log().body()
				.extract().response();

		List<Integer> productIds=response.jsonPath().getList("id",Integer.class);
		assertThat(isSortedDescending(productIds),is(true));


	}


	//5)Test to retrieve products sorted in ascending order
	@Test()
	public void testGetSortedProductsASC()
	{   


		Response response=given()
				.pathParam("order", "asc")


				.when()
				.get(Routes.GET_PRODUCTS_SORTED)

				.then()
				.statusCode(200)
				//.log().body()
				.extract().response();

		List<Integer> productIds=response.jsonPath().getList("id",Integer.class);
		assertThat(isSortedAscending(productIds),is(true));


	}


	//6)Test to get all product categories
	@Test()
	public void testGetAllCategories()
	{   


		given()

		.when()
		.get(Routes.GET_ALL_CATEGORIES)

		.then()
		.statusCode(200)
		.body("size()", greaterThan(0));

	}


	//7)Test to get  products by category
	@Test()
	public void testGetProductsByCategory()
	{   


		given()
		.pathParam("category", "electronics")
		.when()
		.get(Routes.GET_PRODUCTS_BY_CATEGORY)

		.then()
		.statusCode(200)
		.body("size()", greaterThan(0))
		.body("category",everyItem(notNullValue()))
		.body("category",everyItem(equalTo("electronics")));
		//.log().body();
	}


	//8)To Test a Add New Product
	@Test()
	public void testAddNewProduct()
	{   
		Product newProduct=Payload.productPayload();

		int productId=given()
				.contentType(ContentType.JSON)
				.body(newProduct)
				.when()
				.post(Routes.CREATE_PRODUCT)

				.then()
				.statusCode(200)
				//.log().body()
				.body("title", equalTo(newProduct.getTitle()))
				.body("id",notNullValue())
				.extract().jsonPath().getInt("id"); //Extracting Id from response body

		System.out.println("ProductId:"+productId);

	}

	//9)To Test update existing product id 
	@Test()
	public void testUpdateProduct()
	{   
		int productId =configReader.getIntProperty("productId");

		Product updatedPayload=Payload.productPayload();

		given()
		.pathParam("id",productId)
		.contentType(ContentType.JSON)
		.body(updatedPayload)
		.when()
		.put(Routes.UPDATE_PRODUCT)

		.then()
		.statusCode(200)
		//.log().body()
		.body("title", equalTo(updatedPayload.getTitle()))
		.body("id",notNullValue());

	}

		//10)To Test delete existing product id 
		@Test()
		public void testDeleteProduct()
		{   
			int productId =configReader.getIntProperty("productId");



			given()
			.pathParam("id",productId)
			.contentType(ContentType.JSON)

			.when()
			.delete(Routes.DELETE_PRODUCT)

			.then()
			.statusCode(200);
		}
		
	}		