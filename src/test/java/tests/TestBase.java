package tests;

import api.ApiClient;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    protected static ApiClient api;

    @BeforeAll
    public static void setUp() {
        api = new ApiClient();
    }
}