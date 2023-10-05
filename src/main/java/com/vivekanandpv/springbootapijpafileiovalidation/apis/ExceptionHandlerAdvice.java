package com.vivekanandpv.springbootapijpafileiovalidation.apis;

import com.vivekanandpv.springbootapijpafileiovalidation.exceptions.DomainException;
import com.vivekanandpv.springbootapijpafileiovalidation.exceptions.RecordNotFoundException;
import com.vivekanandpv.springbootapijpafileiovalidation.utils.AppUtils;
import com.vivekanandpv.springbootapijpafileiovalidation.viewmodels.ValidationErrorPageViewModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<?> handleDomainException(DomainException de) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", de.getMessage());

        return ResponseEntity.status(400).body(errors);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException(IOException ioe) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", ioe.getMessage());

        return ResponseEntity.status(400).body(errors);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<?> handleRecordNotFoundException(RecordNotFoundException de) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", de.getMessage());

        return ResponseEntity.status(404).body(errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorPageViewModel> handleValidationException(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest().body(AppUtils.getValidationErrorPage(exception, exception.getBindingResult().getTarget().getClass()));
    }
}
