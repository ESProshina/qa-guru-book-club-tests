package tests;

import models.login.LoginBodyModel;
import models.login.SuccessfulLoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static specs.login.LoginSpec.loginResponse400Spec;
import static specs.login.LoginSpec.wrongCredentialsLoginResponseSpec;
import static tests.TestData.*;

public class LoginTests extends TestBase {

    @Test
    @DisplayName("Позитивный: Успешная авторизация (200 OK)")
    public void successfulLoginTest() {
        LoginBodyModel loginData = new LoginBodyModel(LOGIN_USERNAME, LOGIN_PASSWORD);

        SuccessfulLoginResponseModel loginResponse = api.auth.login(loginData);

        String actualAccess = loginResponse.access();
        String actualRefresh = loginResponse.refresh();
        assertThat(actualAccess).startsWith(LOGIN_TOKEN_PREFIX);
        assertThat(actualRefresh).startsWith(LOGIN_TOKEN_PREFIX);
        assertThat(actualAccess).isNotEqualTo(actualRefresh);
    }

    @Test
    @DisplayName("Негативный: Авторизация с неверными учетными данными (401 Unauthorized)")
    public void wrongCredentialsLoginTest() {
        LoginBodyModel loginData = new LoginBodyModel(LOGIN_USERNAME, LOGIN_WRONG_PASSWORD);

        var response = api.auth.loginWithSpec(loginData, wrongCredentialsLoginResponseSpec);

        assertThat(response.path("detail").toString()).isEqualTo(LOGIN_WRONG_CREDENTIALS_ERROR);
    }

    @Test
    @DisplayName("Негативный: Авторизация с неверным паролем (401 Unauthorized)")
    public void loginWithInvalidPasswordTest() {
        LoginBodyModel loginData = new LoginBodyModel(LOGIN_USERNAME, LOGIN_WRONG_PASSWORD);

        var response = api.auth.loginWithSpec(loginData, wrongCredentialsLoginResponseSpec);

        assertThat(response.path("detail").toString()).isEqualTo(LOGIN_WRONG_CREDENTIALS_ERROR);
    }

    @Test
    @DisplayName("Негативный: Авторизация с несуществующим пользователем (401 Unauthorized)")
    public void loginWithNonExistentUserTest() {
        LoginBodyModel loginData = new LoginBodyModel(LOGIN_WRONG_USERNAME, LOGIN_PASSWORD);

        var response = api.auth.loginWithSpec(loginData, wrongCredentialsLoginResponseSpec);

        assertThat(response.path("detail").toString()).isEqualTo(LOGIN_WRONG_CREDENTIALS_ERROR);
    }

    @Test
    @DisplayName("Негативный: Авторизация с пустыми полями (400 Bad Request)")
    public void loginWithEmptyCredentialsTest() {
        LoginBodyModel loginData = new LoginBodyModel(EMPTY_STRING, EMPTY_STRING);

        var response = api.auth.loginWithSpec(loginData, loginResponse400Spec);

        assertThat(response.path("username[0]").toString()).isEqualTo(FIELD_REQUIRED_ERROR);
        assertThat(response.path("password[0]").toString()).isEqualTo(FIELD_REQUIRED_ERROR);
    }
}