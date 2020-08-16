package br.com.crudrestapi.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.crudrestapi.error.DataIntegrityViolationExceptionDetails;
import br.com.crudrestapi.error.ErrorDetails;
import br.com.crudrestapi.error.ResourceNotFoundDetails;
import br.com.crudrestapi.error.ResourceNotFoundException;
import br.com.crudrestapi.error.ValidationErrorDetails;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfException) {
		ResourceNotFoundDetails rnfDetails = new ResourceNotFoundDetails();
		rnfDetails.setTitle("Resource not found");
		rnfDetails.setStatus(HttpStatus.NOT_FOUND.value());
		rnfDetails.setDetail(rnfException.getMessage());
		rnfDetails.setDeveloperMessage(rnfException.getClass().getName());
		return new ResponseEntity<>(rnfDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleSQLIntegrityConstraintViolationException(
			DataIntegrityViolationException divException) {
		DataIntegrityViolationExceptionDetails divDetails = new DataIntegrityViolationExceptionDetails();
		final String TITLE = "Duplicated entry";
		divDetails.setTitle(TITLE);
		divDetails.setStatus(HttpStatus.CONFLICT.value());
		divDetails.setDetail(divException.getRootCause().getMessage());
		divDetails.setDeveloperMessage(divDetails.getClass().getName());
		return new ResponseEntity<>(divDetails, HttpStatus.CONFLICT);
	}

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manvException,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ValidationErrorDetails veDetails = new ValidationErrorDetails();
		Map<String, ArrayList<String>> fieldsErrors = new HashMap<>();
		final String DETAIL_TITLE = "Field validation error";
		manvException.getBindingResult().getFieldErrors().stream().forEach(x -> {
			String key = x.getField();
			if (fieldsErrors.containsKey(x.getField())) {
				ArrayList<String> values = fieldsErrors.get(key);
				values.add(x.getDefaultMessage());
				System.out.println(fieldsErrors.get(x.getField()) == null);
				fieldsErrors.replace(key, values);
			} else {
				ArrayList<String> init = new ArrayList<>();
				init.add(x.getDefaultMessage());
				fieldsErrors.put(key, init);
			}
		});
		veDetails.setTitle(DETAIL_TITLE);
		veDetails.setDetail(DETAIL_TITLE);
		veDetails.setStatus(HttpStatus.BAD_REQUEST.value());
		veDetails.setDeveloperMessage(manvException.getClass().getName());
		veDetails.setFieldsErrors(fieldsErrors);
		return new ResponseEntity<>(veDetails, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception rnfException, @Nullable Object body,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails();
		final String TITLE = "Internal Exception";
		errorDetails.setTitle(TITLE);
		errorDetails.setStatus(status.value());
		errorDetails.setDetail(rnfException.getMessage());
		errorDetails.setDeveloperMessage(rnfException.getClass().getName());
		return new ResponseEntity<>(errorDetails, headers, status);
	}
	
	@ExceptionHandler(PropertyReferenceException.class)
	protected ResponseEntity<?> handlePropertyReferenceException(PropertyReferenceException prException) {
		ErrorDetails errorDetails = new ErrorDetails();
		final String TITLE = "Internal Server Error";
		errorDetails.setTitle(TITLE);
		errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorDetails.setDetail(prException.getMessage());
		errorDetails.setDeveloperMessage(prException.getClass().getName());
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
