package br.com.crudrestapi.util;

import static org.junit.Assert.assertTrue;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@DisplayName("Password.class Tests")
class PasswordEncoderTest {

    @ParameterizedTest
    @ValueSource(strings = { "test123" })
    @DisplayName("Encode Given Password")
    void encodeShouldReturnEncodedPassword(String plainTextPassword) {
	String encodedPassword = PasswordEncoder.encode(plainTextPassword);
	Assertions.assertThat(new BCryptPasswordEncoder().matches("test123", encodedPassword)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({ "test123,test123" })
    @DisplayName("Password Encode Validation")
    void checkEncodedPassword(String plainTextPassword, String encodedPassword) {
	encodedPassword = new BCryptPasswordEncoder().encode(encodedPassword);
	assertTrue(PasswordEncoder.passwordEncodeValidation(plainTextPassword, encodedPassword));
    }
}
