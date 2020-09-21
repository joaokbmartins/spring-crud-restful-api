package br.com.crudrestapi.javaclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;

public class JavaClientTest {

    public static void main(String[] args) {

	HttpURLConnection connection = null;
	BufferedReader reader = null;

	String username = "roberto@gmail.com";
	String password = "teste123";
	String encodedCredentials = returnBased64Credentials(username, password);
//	System.out.println(encodedCredentials);

	try {
	    URL url = new URL("http://localhost:8080/api/v1/internal/users/");
	    connection = (HttpURLConnection) url.openConnection();
	    connection.setRequestMethod("GET");
	    connection.addRequestProperty("Authorization", "Basic " + encodedCredentials);
	    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    StringBuilder jsonSB = new StringBuilder();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
		jsonSB.append(line);
	    }
	    System.out.println(jsonSB);
	} catch (Exception e) {
	    System.err.println("Error: " + e.getMessage());
	} finally {
	    IOUtils.closeQuietly(reader);
	    if (connection != null) {
		connection.disconnect();
	    }
	}

    }

    private static String returnBased64Credentials(String username, String password) {
	String encodedCredentials = username + ":" + password;
	return new String(Base64.encodeBase64(encodedCredentials.getBytes()));
    }

}
