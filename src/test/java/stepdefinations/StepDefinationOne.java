package stepdefinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefinationOne extends Utils {

	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static String place_id;

	@Given("Add Place PayLoad {string} {string} {string}")
	public void add_Place_PayLoad(String name, String language, String address) throws IOException {
		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		res = given().spec(requestSpecification()).body(data.addPlacePayLoad(name, language, address));
	}

	@When("User calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String httpmethod) {

		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		/*
		 * if (httpmethod.equals("POST")) { response =
		 * res.when().post(APIResources.valueOf(resource).getResource()).then().spec(
		 * resspec).extract() .response(); } else if (httpmethod.equals("GET")) {
		 * response =
		 * res.when().get(APIResources.valueOf(resource).getResource()).then().spec(
		 * resspec).extract() .response(); }
		 */

		if (httpmethod.equalsIgnoreCase("POST")) {
			response = res.when().post(APIResources.valueOf(resource).getResource());
		} else if (httpmethod.equalsIgnoreCase("GET")) {
			response = res.when().get(APIResources.valueOf(resource).getResource());

		}

	}

	@Then("The API calls got success with status code {string}")
	public void the_API_calls_got_success_with_status_code(String statuscode) {

		assertEquals(response.getStatusCode(), Integer.parseInt(statuscode));

	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String key, String value) {
		System.out.println(response);
		System.out.println(key);
		System.out.println(value);
		assertEquals(getJsonPath(response, key), value);

	}

	@Then("Verify {string} created maps to {string} using {string}")
	public void verify_created_maps_to_using(String key, String expectedname, String resource) throws IOException {
		place_id = getJsonPath(response, key);
		res = given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_http_request(resource, "GET");
		String actualname = getJsonPath(response, "name");
		assertEquals(expectedname, actualname);
	}

	@Given("DeletePlace Payload")
	public void deleteplace_Payload() throws IOException {
		System.out.println(place_id);
		res = given().spec(requestSpecification()).body(data.deletePlacePayLoad(place_id));
		System.out.println(res);
		/**
		 * {\r\n\"place_id\":\"141f594dcee249f05f6e05ffa8f64a7e\"\r\n}\r\n
		 */
	}

}
