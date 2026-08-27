package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import models.user.UpdateUserBodyModel;
import models.user.UserResponseModel;

import static io.restassured.RestAssured.given;
import static specs.registration.RegistrationSpec.*;
import static specs.user.UserSpec.userRequestSpec;
import static specs.user.UserSpec.userResponse200Spec;

public class UsersApiClient {

    public SuccessfulRegistrationResponseModel register(RegistrationBodyModel body) {
        return given(registrationRequestSpec)
                .body(body)
                .when()
                .post("/users/register/")
                .then()
                .spec(successfulRegistrationResponseSpec)
                .extract()
                .as(SuccessfulRegistrationResponseModel.class);
    }

    public ExistingUserResponseModel registerExistingUser(RegistrationBodyModel body) {
        return given(registrationRequestSpec)
                .body(body)
                .when()
                .post("/users/register/")
                .then()
                .spec(existingUserRegistrationResponseSpec)
                .extract()
                .as(ExistingUserResponseModel.class);
    }

    public Response registerWithSpec(RegistrationBodyModel body, ResponseSpecification spec) {
        return given(registrationRequestSpec)
                .body(body)
                .when()
                .post("/users/register/")
                .then()
                .spec(spec)
                .extract()
                .response();
    }
    @Step("Обновление данных пользователя")
    public UserResponseModel updateUser(String token, UpdateUserBodyModel body) {
        return given(userRequestSpec)
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .patch("/users/me/") // или /users/profile/ в зависимости от API
                .then()
                .spec(userResponse200Spec)
                .extract()
                .as(UserResponseModel.class);
    }

    @Step("Обновление данных пользователя со спецификацией")
    public Response updateUserWithSpec(String token, UpdateUserBodyModel body, ResponseSpecification spec) {
        var request = given(userRequestSpec).body(body);
        if (token != null && !token.isEmpty()) {
            request.header("Authorization", "Bearer " + token);
        }
        return request
                .when()
                .patch("/users/me/")
                .then()
                .spec(spec)
                .extract()
                .response();
    }
}