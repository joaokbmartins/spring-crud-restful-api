package br.com.crudrestapi.javaclient;

import br.com.crudrestapi.entity.User;

public class JavaSpringClient {
    public static void main(String[] args) {

//	User getUser = new JavaClientDAO().findById(1);
//	System.out.println(getUser);

	User newUser = new User();
//	newUser.setId(999);
	newUser.setAdmin(false);
	newUser.setEmail("");
//	newUser.setPassword("teste123");

//	newUser = new JavaClientDAO().update(newUser);
//	newUser = new JavaClientDAO().save(newUser);
	new JavaClientDAO().delete(newUser);

//	System.out.println(newUser);

//	List<User> users = new JavaClientDAO().listAll();
//	System.out.println(users);

    }

}
