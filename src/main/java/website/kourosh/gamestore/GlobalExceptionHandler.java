package website.kourosh.gamestore;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<String> globalExceptionHandler(RuntimeException runtimeException) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(LocalDateTime.now() + "\n\n" + runtimeException.getMessage());
    }

    // TODO add more specific exception handlers
}
