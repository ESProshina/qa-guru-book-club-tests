package tests;

import models.login.LoginBodyModel;
import models.user.UpdateUserBodyModel;
import models.user.UserResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static specs.user.UserSpec.*;
import static tests.TestData.*;

public class UpdateUserTests extends TestBase {

    private String accessToken;

    @BeforeEach
    public void auth() {
        LoginBodyModel loginData = new LoginBodyModel(LOGIN_USERNAME, LOGIN_PASSWORD);
        accessToken = api.auth.loginAndGetAccessToken(loginData);
    }

    @Test
    @DisplayName("Позитивный: Успешное обновление имени, фамилии и email")
    public void successfulUpdateUserTest() {
        UpdateUserBodyModel updateData = new UpdateUserBodyModel(
                UPDATED_FIRST_NAME,
                UPDATED_LAST_NAME,
                UPDATED_EMAIL
        );

        UserResponseModel response = api.users.updateUser(accessToken, updateData);

        assertThat(response.firstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(response.lastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(response.email()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @DisplayName("Негативный: Обновление с некорректным форматом email (400 Bad Request)")
    public void updateUserWithInvalidEmailTest() {
        UpdateUserBodyModel updateData = new UpdateUserBodyModel(
                UPDATED_FIRST_NAME,
                UPDATED_LAST_NAME,
                INVALID_EMAIL
        );

        var response = api.users.updateUserWithSpec(accessToken, updateData, userResponse400Spec);

        assertThat(response.path("email[0]").toString()).isEqualTo(INVALID_EMAIL_ERROR);
    }

    @Test
    @DisplayName("Негативный: Обновление пользователя без токена авторизации (401 Unauthorized)")
    public void updateUserWithoutTokenTest() {
        UpdateUserBodyModel updateData = new UpdateUserBodyModel(
                UPDATED_FIRST_NAME,
                UPDATED_LAST_NAME,
                UPDATED_EMAIL
        );

        var response = api.users.updateUserWithSpec(EMPTY_STRING, updateData, userResponse401Spec);

        assertThat(response.path("detail").toString()).isEqualTo(UNAUTHORIZED_ERROR);
    }
}