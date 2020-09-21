package br.com.crudrestapi.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.crudrestapi.error.ErrorDetails;
import br.com.crudrestapi.error.ResourceNotFoundException;
import br.com.crudrestapi.error.ValidationErrorDetails;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
	    MethodArgumentNotValidException ex,
	    HttpHeaders headers,
	    HttpStatus status,
	    WebRequest request) {
	ValidationErrorDetails validationErrorDetails = new ValidationErrorDetails();
	validationErrorDetails.setTitle(status.getReasonPhrase());
	validationErrorDetails.setDetail(ex.getMessage());
	validationErrorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
	validationErrorDetails.setException(ex.getClass().getName());
	validationErrorDetails.setFieldsAndErrors(validationErrorDetails.validateFields(ex));
	return new ResponseEntity<>(validationErrorDetails, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
	    Exception ex,
	    @Nullable Object body,
	    HttpHeaders headers,
	    HttpStatus status,
	    WebRequest request) {
	ErrorDetails errorDetails = getErrorDetails(status, ex);
	return new ResponseEntity<>(errorDetails, headers, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
	return getResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
	return getResponseEntity(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleSQLIntegrityConstraintViolationException(DataIntegrityViolationException ex) {
	return getResponseEntity(ex, HttpStatus.CONFLICT);
    }

//	 @ExceptionHandler(NoHandlerFoundException.class)
//	 public ResponseEntity<?> handleRequestRejectedException(NoHandlerFoundException ex) {
//			return getResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
//	 }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
	    NoHandlerFoundException ex,
	    HttpHeaders headers,
	    HttpStatus status,
	    WebRequest request) {
//	System.out.println("Ta vind pra ca");
	return handleExceptionInternal(ex, null, headers, status, request);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    protected ResponseEntity<?> handlePropertyReferenceException(PropertyReferenceException ex) {
	return getResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorDetails getErrorDetails(HttpStatus status, Exception ex) {
	ErrorDetails errorDetails = new ErrorDetails();
	errorDetails.setTitle(status.getReasonPhrase());
	errorDetails.setStatus(status.value());
	errorDetails.setDetail(ex.getLocalizedMessage());
	errorDetails.setException(ex.getClass().getName());
	return errorDetails;
    }

    private ResponseEntity<?> getResponseEntity(Exception ex, HttpStatus status) {
	return new ResponseEntity<>(getErrorDetails(status, ex), status);
    }

}
