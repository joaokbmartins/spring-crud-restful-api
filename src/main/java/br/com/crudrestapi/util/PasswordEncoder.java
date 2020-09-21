package br.com.crudrestapi.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    public static String encode(String password) {
	return new BCryptPasswordEncoder().encode(password);
    }

    public static boolean passwordEncodeValidation(String plainPassword, String encodedPassword) {
	return new BCryptPasswordEncoder().matches(plainPassword, encodedPassword);
    }

}
