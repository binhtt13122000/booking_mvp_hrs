package demo.booking.common;

public class ExceptionConstants {
    public static final String VALIDATION_VIOLATION_EXCEPTION_CODE =
            "VALIDATION_VIOLATION_EXCEPTION";
    public static final String VALIDATION_VIOLATION_EXCEPTION_MESSAGE =
            "Please validation your input";

    public static final String MESSAGE_NOT_READABLE_EXCEPTION_CODE =
            "MESSAGE_NOT_READABLE_EXCEPTION";
    public static final String MESSAGE_NOT_READABLE_EXCEPTION_MESSAGE =
            "MESSAGE_NOT_READABLE_EXCEPTION";

    public static final String CONSTRAINT_VIOLATION_EXCEPTION_CODE =
            "CONSTRAINT_VIOLATION_EXCEPTION";
    public static final String CONSTRAINT_VIOLATION_EXCEPTION_MESSAGE =
            "CONSTRAINT_VIOLATION_EXCEPTION";

    public static final String INTERNAL_AUTHENTICATION_SERVICE_EXCEPTION_CODE =
            "INTERNAL_AUTHENTICATION_SERVICE_EXCEPTION_CODE";
    public static final String INTERNAL_AUTHENTICATION_SERVICE_EXCEPTION_MESSAGE =
            "Authentication failed at controller advice";

    public static final String ACCESS_DENIED_EXCEPTION_CODE = "ACCESS_DENIED_EXCEPTION";
    public static final String ACCESS_DENIED_EXCEPTION_MESSAGE =
            "You can not access this resource due to credential permission";

    public static final String BAD_CREDENTIAL_EXCEPTION_CODE = "BAD_CREDENTIAL_EXCEPTION";
    public static final String BAD_CREDENTIAL_EXCEPTION_MESSAGE =
            "The username or password is wrong, please try again";

    public static final String INTERNAL_SERVER_ERROR_CODE = "INTERNAL_SERVER_ERROR";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "INTERNAL_SERVER_ERROR";

    public static final String DATE_TIME_PARSER_EXCEPTION_CODE = "DATE_TIME_PARSER_EXCEPTION";
    public static final String DATE_TIME_PARSER_EXCEPTION_MESSAGE = "DATE_TIME_PARSER_EXCEPTION";
}
