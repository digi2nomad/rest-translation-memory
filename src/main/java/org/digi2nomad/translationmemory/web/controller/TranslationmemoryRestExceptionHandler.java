package org.digi2nomad.translationmemory.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Exception handler for the translation memory REST API.
 */
@ControllerAdvice
public class TranslationmemoryRestExceptionHandler {

	/**
	 * return 404
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
		ErrorResponse errorResponse = ErrorResponse.create(ex, 
				HttpStatus.NOT_FOUND, "The requested resource was not found");
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
    /**
     * return 400
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
    	ErrorResponse errorResponse = ErrorResponse.create(ex, 
    			HttpStatus.BAD_REQUEST, "Invalid argument provided");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}