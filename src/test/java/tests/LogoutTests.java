package tests;

import models.login.LoginBodyModel;
import models.logout.LogoutBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static specs.logout.LogoutSpec.logoutResponse400Spec;
import static specs.logout.LogoutSpec.logoutResponse401Spec;
import static tests.TestData.*;

public class LogoutTests extends TestBase {

    @Test
    @DisplayName("Позитивный: Успешный разлогин с валидным refresh-токеном")
    public void successfulLogoutTest() {
        LoginBodyModel loginData = new LoginBodyModel(LOGIN_USERNAME, LOGIN_PASSWORD);
        String refreshToken = api.auth.loginAndGetRefreshToken(loginData);

        LogoutBodyModel logoutBody = new LogoutBodyModel(refreshToken);
        api.auth.logout(logoutBody);
    }

    @Test
    @DisplayName("Негативный: Разлогин с недействительным refresh-токеном (401 Unauthorized)")
    public void logoutWithInvalidTokenTest() {
        LogoutBodyModel logoutBody = new LogoutBodyModel(INVALID_REFRESH_TOKEN);

        var response = api.auth.logoutWithSpec(logoutBody, logoutResponse401Spec);

        assertThat(response.path("detail").toString()).isEqualTo(INVALID_TOKEN_ERROR);
        assertThat(response.path("code").toString()).isEqualTo("token_not_valid");
    }

    @Test
    @DisplayName("Негативный: Разлогин с пустым refresh-токеном (400 Bad Request)")
    public void logoutWithEmptyTokenTest() {
        LogoutBodyModel logoutBody = new LogoutBodyModel(EMPTY_STRING);

        var response = api.auth.logoutWithSpec(logoutBody, logoutResponse400Spec);

        assertThat(response.path("refresh[0]").toString()).isEqualTo(FIELD_REQUIRED_ERROR);
    }
}