package br.com.crudrestapi.error;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
public class ValidationErrorDetails extends ErrorDetails {

    private Map<String, List<String>> fieldsAndErrors;

    public Map<String, List<String>> getFieldsAndErrors() {
	return fieldsAndErrors;
    }

    public void setFieldsAndErrors(Map<String, List<String>> fieldsAndErrors) {
	this.fieldsAndErrors = fieldsAndErrors;
    }

    public Map<String, List<String>> validateFields(MethodArgumentNotValidException ex) {
	Map<String, List<String>> mappedFieldsAndErros = new HashMap<>();
	ex.getBindingResult().getFieldErrors().stream().forEach(errorFieldDetails -> {
	    String field = errorFieldDetails.getField();
	    if (mappedFieldsAndErros.containsKey(errorFieldDetails.getField())) {
		mappedFieldsAndErros.replace(
			field,
			Stream.of(mappedFieldsAndErros.get(field), Arrays.asList(errorFieldDetails.getDefaultMessage()))
				.flatMap(aux -> aux.stream())
				.collect(Collectors.toList()));
	    } else {
		mappedFieldsAndErros.put(field, Arrays.asList(errorFieldDetails.getDefaultMessage()));
	    }
	});
	return mappedFieldsAndErros;
    }

}
