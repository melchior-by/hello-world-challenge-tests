package org.example.tests;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.services.PropertiesService;

import java.util.logging.Level;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;

public class GetHelloStepDefinitions {
    private final static Logger LOGGER = Logger.getLogger(PropertiesService.class.getName());
    private final static String BASE_ENDPOINT = "http://localhost:8080/challenge";
    private final static String X_SESSION_ID = "X-Session-Id";

    private String token = new PropertiesService().loadProperty("token");

    private String endpoint;
    private Response response;
    private RequestSpecification request;

    @Given("request to {string} endpoint")
    public void requestToEndpoint(String endpoint) {
        this.endpoint = String.format("%s/%s", BASE_ENDPOINT, endpoint);
    }

    @And("User authorization is {string}")
    public void userAuthorizationIs(String isAuthorized) {
        switch (isAuthorized) {
            case "authorized":
                request = given().header("X-Session-Id", token);
                break;
            case "no authorization":
                request = given();
                break;
            case "anything else!":
                /*It fails. Placed here as example - if we didn't check unsupported header we can change status code to 200,
                 * else if we check - it also can be changed to 500.*/
                request = given().header(X_SESSION_ID, token).
                        header("wrong_header", "wrong_value");
                break;
        }
    }

    @When("GET request")
    public void getRequest() {
        LOGGER.log(Level.INFO, String.format("GET request to the %s endpoint", endpoint));
        response = request.when().get(endpoint);
    }

    @Then("I should get {int} status code")
    public void iShouldGetStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
    }
}
