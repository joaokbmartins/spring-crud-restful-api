package br.com.crudrestapi.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class InputStreamParser {

    public static String parse(InputStream inputStream) throws IOException {
	StringBuilder stringBuilder = new StringBuilder();
	Reader reader = new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name()));
	int counter = 0;
	while ((counter = reader.read()) != -1) {
	    stringBuilder.append((char) counter);
	}
	return stringBuilder.toString();
    }

}
