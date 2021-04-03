package securitypoc.config;

public final class Constants {

    // ROLES LIST TO BE CONFORM WITH ENUM Role
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    // Base Uri list
    public static final String API_USER_URI = "/api/users";

    // Error massage list
    public static final String ERROR_TAG = "@@@@@@@@@@ ";
    public static final String INVALID_USERNAME_PASSWORD_SUPPLIED = "Invalid username/password supplied";
    public static final String USERNAME_IS_ALREADY_IN_USE = "Username is already in use";
    public static final String THE_USER_DOESN_T_EXIST = "The user doesn't exist";
}
