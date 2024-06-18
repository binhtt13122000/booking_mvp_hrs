package demo.booking.projector.response;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class BaseResponse<T> {
    protected static final String SUCCEED_REQUEST_MESSAGE = "Success";
    private T data;
    private String message;
    private String exceptionCode;
    private Map<String, String> errors;

    public static <T> BaseResponse<T> ok(T response) {
        return BaseResponse.<T>builder()
                .data(response)
                .message(SUCCEED_REQUEST_MESSAGE)
                .exceptionCode(null)
                .build();
    }

    public static <T> BaseResponse<T> error(String exceptionCode, String exceptionMessage, Map<String, String> errors) {
        return BaseResponse.<T>builder()
                .data(null)
                .message(exceptionMessage)
                .exceptionCode(exceptionCode)
                .errors(errors)
                .build();
    }

    public static <T> BaseResponse<T> error(String exceptionCode, String exceptionMessage) {
        return BaseResponse.<T>builder()
                .data(null)
                .message(exceptionMessage)
                .exceptionCode(exceptionCode)
                .build();
    }

    public static <T> ResponseEntity<BaseResponse<T>> buildOkResponse(T t) {
        return ResponseEntity.ok(BaseResponse.ok(t));
    }

    public static <T> ResponseEntity<BaseResponse<T>> buildCreatedResponse(T t) {
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.ok(t));
    }
}
