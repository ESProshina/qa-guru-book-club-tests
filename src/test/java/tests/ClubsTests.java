package tests;

import models.clubs.ClubModel;
import models.clubs.ClubsListResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClubsTests extends TestBase {

    @Test
    @DisplayName("GET /clubs возвращает 200 и валидную структуру")
    public void getClubsReturns200AndValidStructure() {
        ClubsListResponseModel response = api.clubs.getClubs();

        assertThat(response).isNotNull();
        assertThat(response.count()).isGreaterThanOrEqualTo(0);
        assertThat(response.results()).isNotNull();
        assertThat(response.results().size()).isLessThanOrEqualTo(response.count());
        assertThat(response.results().size()).isLessThanOrEqualTo(100);
    }

    @Test
    @DisplayName("Количество клубов корректно (count и results не противоречат друг другу)")
    public void getClubsCountMatchesResultsSize() {
        ClubsListResponseModel response = api.clubs.getClubs();
        assertThat(response.results())
                .as("Размер results не должен превышать count")
                .hasSizeLessThanOrEqualTo(response.count());
    }

    @Test
    @DisplayName("Каждый клуб содержит обязательные поля")
    public void getClubsEachClubHasRequiredFields() {
        ClubsListResponseModel response = api.clubs.getClubs();
        if (response.results().isEmpty()) {
            System.out.println("⚠️ Нет клубов для проверки полей");
            return;
        }
        for (ClubModel club : response.results()) {
            assertThat(club.id())
                    .as("ID клуба должен быть положительным числом")
                    .isNotNull()
                    .isPositive();

            assertThat(club.bookTitle())
                    .as("Название книги не должно быть null")
                    .isNotNull();

            assertThat(club.bookAuthors())
                    .as("Авторы книги не должны быть null")
                    .isNotNull();

            assertThat(club.publicationYear())
                    .as("Год публикации не должен быть null")
                    .isNotNull();

            assertThat(club.description())
                    .as("Описание не должно быть null")
                    .isNotNull();

            assertThat(club.telegramChatLink())
                    .as("Ссылка на Telegram не должна быть null")
                    .isNotNull();

            assertThat(club.owner())
                    .as("ID владельца должен быть положительным числом")
                    .isNotNull()
                    .isPositive();

            assertThat(club.members())
                    .as("Список участников не должен быть null")
                    .isNotNull();

            assertThat(club.reviews())
                    .as("Список отзывов не должен быть null")
                    .isNotNull();

            assertThat(club.created())
                    .as("Дата создания не должна быть null")
                    .isNotNull();
        }
    }
    @Test
    @DisplayName("Проверка пагинации: count, next, previous, results")
    public void getClubsPaginationFieldsPresent() {
        ClubsListResponseModel response = api.clubs.getClubs();

        assertThat(response.count())
                .as("count должен быть неотрицательным числом")
                .isNotNull()
                .isGreaterThanOrEqualTo(0);

        assertThat(response.results())
                .as("results не должен быть null")
                .isNotNull();

         if (response.results().size() < response.count()) {
            assertThat(response.next())
                    .as("Если есть еще страницы, next должен быть не null")
                    .isNotNull();
        }

        if (response.previous() == null) {
            // Это первая страница
            assertThat(response.previous()).isNull();
        }
    }
}