package org.omnikron.aimda.exceptions;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${app.errors.include-details:false}") // Defaults to false if not set
    private boolean includeErrorDetails;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException exception) {
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Resource not found");

        if (includeErrorDetails) 
        {
            body.put("message", exception.getMessage());
        }
        else
        {
            body.put("message", "The resource you are looking for might have been removed, had its name changed, or is temporarily unavailable.");
        }

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNotFound(NoHandlerFoundException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");

        if (includeErrorDetails) 
        {
            body.put("message", exception.getMessage());
        }
        else
        {
            body.put("message", "The requested resource was not found.");
        }

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MissingServletRequestPartException.class, MultipartException.class})
    public ResponseEntity<Object> handleBadRequest(Exception exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");

        if (includeErrorDetails)
        {
            body.put("message", exception.getMessage());
        }
        else
        {
            body.put("message", "The request could not be processed. Please check your input.");
        }


        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception exception)
    {
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");

        if (includeErrorDetails)
        {
            body.put("message", exception.getMessage());
        }
        else
        {
            body.put("message", "The server encountered an internal error or misconfiguration and was unable to complete your request.");
        }


        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
