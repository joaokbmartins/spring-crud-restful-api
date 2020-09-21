package br.com.crudrestapi.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.assertj.core.api.Assertions;
//import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import br.com.crudrestapi.util.PasswordEncoder;

class UserTest {

    @Test
    void userShouldCreateABlankUser() {
	User user = new User();
	Assertions.assertThat(user.getId()).isZero();
	Assertions.assertThat(user.getEmail()).isNull();
	Assertions.assertThat(user.getPassword()).isNull();
	Assertions.assertThat(user.isAdmin()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = { 1 })
    void userIdShouldCreateAUserWithOnlyId(long id) {
	User user = new User(id);
	Assertions.assertThat(user.getId()).isEqualTo(id);
	Assertions.assertThat(user.getEmail()).isNull();
	Assertions.assertThat(user.getPassword()).isNull();
	Assertions.assertThat(user.isAdmin()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = { "test@email.com" })
    void setEmailShouldSetGivenEmail(String email) {
	User user = new User();
	user.setEmail(email);
	assertNotNull(user.getEmail());
	Assertions.assertThat(user.getEmail()).isNotBlank();
	assertEquals("test@email.com", user.getEmail());
    }

    @Test
    void toStirngShouldDisplayUserData() {
	User user = new User("test@email.com", PasswordEncoder.encode("test123"), true);
	user.setId(1);
	assertEquals(
		"User [id=" + user.getId() + " email=" + user.getEmail() + ", password=" + user.getPassword()
			+ ", admin=" + user.isAdmin() + "]",
		user.toString());
    }

    @Test
    void getEmailShouldReturnUsersEmail() {
	User user = new User("test@email.com", PasswordEncoder.encode("teste123"), true);
	assertEquals("test@email.com", user.getEmail());
    }

    @Test
    void getPasswordShouldReturnUsersPassword() {
	User user = new User("test@email.com", PasswordEncoder.encode("teste123"), true);
	assertFalse(PasswordEncoder.passwordEncodeValidation("test@email.com", user.getPassword()));
    }

    @Test
    void setAdminShouldSetUserAdminAttribute() {
	User user = new User();
	user.setAdmin(true);
	assertTrue(user.isAdmin());
    }

    @ParameterizedTest
    @ValueSource(strings = "test123")
    void setPasswordShouldSetUsersPassword(String password) {
	User user = new User();
	user.setPassword(password);
	assertEquals(password, user.getPassword());
    }

}
