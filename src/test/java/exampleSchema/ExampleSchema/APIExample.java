package exampleSchema.ExampleSchema;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.DataUtility;

public class APIExample {

	RequestSpecification commonJsonSpec = new RequestSpecBuilder().setBaseUri("https://api-staging-builder.engineer.ai")
			.setContentType(ContentType.JSON).build();
	RequestSpecification loginJsonSpec;

	String token = "";

	@BeforeSuite
	public void login() {
		String loginPayload = "{\"email\":\"jogidemo321@gmail.com\",\"password\":\"builder123\"}";

		Response response = RestAssured.given().spec(commonJsonSpec).body(loginPayload).when().post("/users/sign_in");

		token = response.jsonPath().get("user.authtoken");

	}

	@Test
	public void dashboard() {
		Response response = RestAssured.given().spec(commonJsonSpec).header("authtoken", token)
				.param("status", "completed").when().get("/build_cards");
		assertEquals(response.statusCode(), 200);

		// disini methodnya assert schema yang disimpen di xls. lokasinya di folder
		// resources
		response.then().assertThat()
				.body(matchesJsonSchema(DataUtility.getDataFromExcel("Schemas", "DashboardAPISchema")));
	}

}
