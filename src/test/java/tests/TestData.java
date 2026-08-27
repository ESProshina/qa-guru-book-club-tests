package tests;

public class TestData {

    public static final String LOGIN_USERNAME = "Ainur";
    public static final String LOGIN_PASSWORD = "123456";

    public static final String LOGIN_WRONG_USERNAME = "NonExistentUser123";
    public static final String LOGIN_WRONG_PASSWORD = "wrong_password";
    public static final String EMPTY_STRING = "";
    public static final String FIELD_REQUIRED_ERROR = "This field may not be blank.";

    public static final String INVALID_REFRESH_TOKEN = "invalid_refresh_token_12345";
    public static final String INVALID_TOKEN_ERROR = "Token is invalid or expired";

    public static final String LOGIN_TOKEN_PREFIX = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
    public static final String REGISTRATION_IP_REGEXP =
            "^((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}"
                    + "(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)$";

    public static final String UPDATED_FIRST_NAME = "Ainur_Updated";
    public static final String UPDATED_LAST_NAME = "QA_Engineer";
    public static final String UPDATED_EMAIL = "ainur_updated@example.com";
    public static final String INVALID_EMAIL = "invalid_email_format";

    public static final String INVALID_EMAIL_ERROR = "Enter a valid email address.";
    public static final String UNAUTHORIZED_ERROR = "Authentication credentials were not provided.";

    public static final String LOGIN_WRONG_CREDENTIALS_ERROR = "Invalid username or password.";
    public static final String REGISTRATION_EXISTING_USER_ERROR = "A user with that username already exists.";
}