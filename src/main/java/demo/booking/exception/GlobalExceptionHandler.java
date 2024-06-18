package demo.booking.exception;

import demo.booking.common.ExceptionConstants;
import demo.booking.projector.response.BaseResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> wrapWithErrorResponse(
            String code, String message, HttpStatus status, HashMap<String, String> errors) {
        return new ResponseEntity<>(BaseResponse.error(code, message, errors), status);
    }

    private ResponseEntity<Object> wrapWithErrorResponse(
            String code, String message, HttpStatus status) {
        return new ResponseEntity<>(BaseResponse.error(code, message), status);
    }

    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBaseDomainException(BaseException exception) {
        return wrapWithErrorResponse(
                exception.getCode(),
                exception.getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException exception) {
        return wrapWithErrorResponse(
                ExceptionConstants.BAD_CREDENTIAL_EXCEPTION_CODE,
                ExceptionConstants.BAD_CREDENTIAL_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        log.error(exception.getMessage());
        return wrapWithErrorResponse(
                ExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
                ExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
                HttpStatus.INTERNAL_SERVER_ERROR,
                new HashMap<>(Map.of("error", exception.getMessage())));
    }

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleTime(RuntimeException exception) {
        log.error(exception.getMessage());
        return wrapWithErrorResponse(
                ExceptionConstants.DATE_TIME_PARSER_EXCEPTION_CODE,
                ExceptionConstants.DATE_TIME_PARSER_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST,
                new HashMap<>(Map.of("error", exception.getMessage())));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException exception) {
        return wrapWithErrorResponse(
                ExceptionConstants.CONSTRAINT_VIOLATION_EXCEPTION_CODE,
                ExceptionConstants.CONSTRAINT_VIOLATION_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(
            HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return wrapWithErrorResponse(
                ExceptionConstants.ACCESS_DENIED_EXCEPTION_CODE,
                ExceptionConstants.ACCESS_DENIED_EXCEPTION_MESSAGE,
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public ResponseEntity<Object> handleAuthenticationException(Exception ex) {
        return wrapWithErrorResponse(
                ExceptionConstants.INTERNAL_AUTHENTICATION_SERVICE_EXCEPTION_CODE,
                ExceptionConstants.INTERNAL_AUTHENTICATION_SERVICE_EXCEPTION_MESSAGE,
                HttpStatus.UNAUTHORIZED);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        HashMap<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach((FieldError error) -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        exception.getBindingResult().getGlobalErrors().forEach((ObjectError error) -> {
            errors.put(error.getObjectName(), error.getDefaultMessage());
        });

        return wrapWithErrorResponse(
                ExceptionConstants.VALIDATION_VIOLATION_EXCEPTION_CODE,
                ExceptionConstants.VALIDATION_VIOLATION_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST,
                errors);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull HttpMessageNotReadableException exception,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request) {
        return wrapWithErrorResponse(
                ExceptionConstants.MESSAGE_NOT_READABLE_EXCEPTION_CODE,
                ExceptionConstants.MESSAGE_NOT_READABLE_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST);
    }
}
