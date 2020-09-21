package br.com.crudrestapi.handler;

import java.io.IOException;

import org.apache.commons.io.IOUtil;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.DefaultResponseErrorHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends DefaultResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
	System.out.println("**********************************Inside hasError method...");
	return super.hasError(response);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>  STATUS: " + response.getRawStatusCode());
	System.out.println("------------------ Body: " + IOUtil.toString(response.getBody(), "UTF-8"));
//	super.handleError(response);
    }

}
