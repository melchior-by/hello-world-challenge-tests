package org.example.services;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class UserAuthService {
    private static final String BASE_ENDPOINT = "http://localhost:8080/challenge";
    private static final String CLIENTS = "/clients";
    private static final String LOGIN = "/login";

    private static UserAuthService instance;

    public static synchronized UserAuthService getInstance() {
        if (instance == null) {
            instance = new UserAuthService();
        }
        return instance;
    }

    public String getValidToken() {
        return doLogin(getRegisteredUserName());
    }

    private String doLogin(String registeredUserName) {
        JSONObject requestBody = new JSONObject().put("username", registeredUserName);
        RequestSpecification request = given().contentType(JSON).body(requestBody.toString());

        Response response = request.post(BASE_ENDPOINT + LOGIN);
        return response.then().assertThat().statusCode(HttpStatus.SC_OK).extract().header("X-Session-Id");
    }


    public String getRegisteredUserName() {
        ArrayList<String> clients = getClients();
        if (clients.isEmpty()) {
            postClient();
            return getClients().get(0);
        }

        return clients.get(0);
    }

    private ArrayList<String> getClients() {
        Response response = get(BASE_ENDPOINT + CLIENTS);

        return response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract().jsonPath().get("clients");
    }

    private void postClient() {
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        String fullName = uuid.substring(0, 16);
        String userName = uuid.substring(17);
        JSONObject requestBody = new JSONObject().put("fullName", "fN" + fullName).put("username", "uN" + userName);
        RequestSpecification request = given().contentType(JSON).body(requestBody.toString());

        Response response = request.post(BASE_ENDPOINT + CLIENTS);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
    }
}
