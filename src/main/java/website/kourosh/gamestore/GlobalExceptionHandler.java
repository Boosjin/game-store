package website.kourosh.gamestore;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<String> constraintViolationException(ConstraintViolationException e) {

        StringBuilder violations = new StringBuilder("Constraint Violation(s)\n\n");
        e.getConstraintViolations().forEach(constraintViolation -> violations.append(constraintViolation.getMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(violations.toString());
    }

    // TODO add more specific exception handlers
}
