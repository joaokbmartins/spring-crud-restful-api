package br.com.crudrestapi.javaclient;

import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.crudrestapi.entity.CustomPageableResponse;
import br.com.crudrestapi.entity.User;
import br.com.crudrestapi.handler.RestResponseExceptionHandler;

public class JavaClientDAO {

    String username = "admin@gmail.com";
    String password = "teste123";
    RestTemplate restTemplate = new RestTemplateBuilder().rootUri("http://localhost:8080/api/v1/internal/users")
	    .basicAuthentication(username, password)
	    .errorHandler(new RestResponseExceptionHandler())
	    .build();
    RestTemplate adminRestTemplate = new RestTemplateBuilder().rootUri("http://localhost:8080/api/v1/admin/users")
	    .basicAuthentication(username, password)
	    .errorHandler(new RestResponseExceptionHandler())
	    .build();

    public User findById(long id) {
	return restTemplate.getForObject("/{id}", User.class, id);
    }

    public List<User> listAll() {
	ResponseEntity<CustomPageableResponse<User>> exchange = restTemplate
		.exchange("/", HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageableResponse<User>>() {
		});
	return exchange.getBody().toList();
    }

    public User save(User user) {
	ResponseEntity<User> exchange = adminRestTemplate
		.exchange("/", HttpMethod.POST, new HttpEntity<>(user, createJSONHeader()), User.class);
	return exchange.getBody();
    }

    public User update(User user) {
	ResponseEntity<User> exchange = adminRestTemplate
		.exchange("/", HttpMethod.PUT, new HttpEntity<>(user, createJSONHeader()), User.class);
	return exchange.getBody();
    }

    public void delete(User user) {
	adminRestTemplate.exchange("/", HttpMethod.DELETE, new HttpEntity<>(user, createJSONHeader()), User.class);
    }

    public static HttpHeaders createJSONHeader() {
	HttpHeaders header = new HttpHeaders();
	header.setContentType(MediaType.APPLICATION_JSON);
	return header;
    }

//     public void update(User user) {
//	adminRestTemplate.put("/", user);
//    }
//
//    public void delete(User user) {
//	adminRestTemplate.delete("/", user);
//    }

}

//
//ResponseEntity<User> resUser = restTemplate.getForEntity("", User.class);
//System.out.println(resUser.getBody());

//User[] users = restTemplate.getForObject("/", User[].class);
//System.out.println(Arrays.toString(users));

//ResponseEntity<CustomPageableResponse<User>> users = restTemplate.exchange(
//	"/?sort=admin,asc&sort=id,desc",
//	HttpMethod.GET,
//	null,
//	new ParameterizedTypeReference<CustomPageableResponse<User>>() {
//	});

//User user = new User();
//user.setAdmin(false);
//user.setEmail("tesrte@g3mdfaixcbxssl.cofm" + Math.random() *1);
//user.setPassword("dfsdfsdfsdffsdf");
//
////    ResponseEntity<User> exchangePostUser = adminRestTemplate
////	    .exchange("/", HttpMethod.POST, new HttpEntity<>(user, createJSONHeader()), User.class);
////    System.out.println();
//
//User objectPostUser = adminRestTemplate.postForObject("/", user, User.class); 
//ResponseEntity<User> entityPostUser = adminRestTemplate.postForEntity("/", user, User.class);
//
//System.out.println(entityPostUser);
