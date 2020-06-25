package org.example.tests;

import io.cucumber.java.Before;
import org.example.services.PropertiesService;
import org.example.services.UserAuthService;

public class Hooks {

    @Before
    public void prepareData() {
        UserAuthService userAuthService = UserAuthService.getInstance();
        String token = userAuthService.getValidToken();
        new PropertiesService().saveProperty("token", token);
    }

}
