package api;

import io.qameta.allure.Step;
import models.clubs.ClubsListResponseModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.baseRequestSpec;
import static specs.clubs.ClubsSpec.successfulClubsListResponseSpec;

public class ClubsApiClient {

    @Step("Получение списка клубов GET /clubs/")
    public ClubsListResponseModel getClubs() {
        return given(baseRequestSpec)
                .when()
                .get("/clubs/")
                .then()
                .spec(successfulClubsListResponseSpec)
                .extract()
                .as(ClubsListResponseModel.class);
    }
}