package demo.booking.common;

import java.time.format.DateTimeFormatter;

public class Constants {
    public static final String DEFAULT_OFFSET = "0";
    public static final String DEFAULT_LIMIT = "10";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final String PATH_LOGIN = "/api/v1/auth/login";
    public static final String PATH_REGISTER = "/api/v1/auth/register";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String EMPTY = "";
    public static final String TOKEN_TYE = "jwt";
    public static final String TYPE = "type";
    public static final String USER = "ROLE_USER";
}
