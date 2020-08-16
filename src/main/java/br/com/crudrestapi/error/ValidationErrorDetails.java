package br.com.crudrestapi.error;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ValidationErrorDetails extends ErrorDetails {

	private Map<String, ArrayList<String>> fieldsErrors;

	public Map<String, ArrayList<String>> getFieldsErrors() {
		return fieldsErrors;
	}

	public void setFieldsErrors(Map<String, ArrayList<String>> fieldsErrors) {
		this.fieldsErrors = fieldsErrors;
	}

}
