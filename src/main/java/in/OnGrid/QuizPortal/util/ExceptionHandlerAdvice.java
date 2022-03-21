package in.OnGrid.QuizPortal.util;

import in.OnGrid.QuizPortal.model.dto.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<BaseErrorResponse> illegalArgumentErrorHandler(Exception e) {
        BaseErrorResponse ob = new BaseErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ob);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<BaseErrorResponse> accessDeniedErrorHandler(Exception e) {
        BaseErrorResponse ob = new BaseErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ob);
    }
}
