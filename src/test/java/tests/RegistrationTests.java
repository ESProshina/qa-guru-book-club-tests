package tests;

import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static specs.registration.RegistrationSpec.registrationResponse400Spec;
import static tests.TestData.*;

public class RegistrationTests extends TestBase {

    String username;
    String password;

    @BeforeEach
    public void prepareTestData() {
        username = "user_" + System.currentTimeMillis();
        password = "pass_" + System.currentTimeMillis();
    }

    @Test
    @DisplayName("Позитивный: Успешная регистрация")
    public void successfulRegistrationTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel(username, password);

        SuccessfulRegistrationResponseModel registrationResponse =
                api.users.register(registrationData);

        assertThat(registrationResponse.id()).isGreaterThan(0);
        assertThat(registrationResponse.username()).isEqualTo(username);
        assertThat(registrationResponse.firstName()).isEqualTo("");
        assertThat(registrationResponse.lastName()).isEqualTo("");
        assertThat(registrationResponse.email()).isEqualTo("");

        assertThat(registrationResponse.remoteAddr()).matches(REGISTRATION_IP_REGEXP);
    }
    @Test
    @DisplayName("Негативный: Регистрация без пароля (400 Bad Request)")
    public void registrationWithoutPasswordTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel(username, EMPTY_STRING);

        var response = api.users.registerWithSpec(registrationData, registrationResponse400Spec);

        assertThat(response.path("password[0]").toString()).isEqualTo(FIELD_REQUIRED_ERROR);
    }

    @Test
    @DisplayName("Негативный: Регистрация без логина (400 Bad Request)")
    public void registrationWithoutUsernameTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel(EMPTY_STRING, password);

        var response = api.users.registerWithSpec(registrationData, registrationResponse400Spec);

        assertThat(response.path("username[0]").toString()).isEqualTo(FIELD_REQUIRED_ERROR);
    }
}
