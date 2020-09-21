package br.com.crudrestapi.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.crudrestapi.entity.User;
import br.com.crudrestapi.util.PasswordEncoder;

@DataJpaTest
@DisplayName("User Repository Tests")
@RunWith(SpringJUnit4ClassRunner.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    void createShouldPersistData() {
	System.out.println("CREATING USER...........");
	User user = new User("outro@email.com", PasswordEncoder.encode("test123"), false);
	this.userRepository.save(user);
	System.out.println("User >>> " + user);
	Assertions.assertThat(user.getId()).isNotZero();
	Assertions.assertThat(user.getEmail()).isEqualTo("outro@email.com");
	Assertions.assertThat(user.getPassword()).isNotNull();
	Assertions.assertThat(user.isAdmin()).isFalse();
    }

    @Test
    void findAllShouldResturUsersList() {
	System.out.println("SEARCHING USER...........");
	this.userRepository.save(new User("outro@email.com", PasswordEncoder.encode("test123"), false));
	Iterable<User> users = this.userRepository.findAll();
	Assertions.assertThat(users.iterator().next()).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = { 3 })
    void findByIdShouldResturUserWithGivenId(long id) {
	System.out.println("SEARCHING USER WITH GIVEN ID..........." + id);

	User user = new User("findByIdEmailTest@email.com", PasswordEncoder.encode("test123"), false);
	this.userRepository.save(user);
	user = this.userRepository.findById(user.getId());

	Assertions.assertThat(user.getId()).isEqualTo(id);
	assertEquals("findByIdEmailTest@email.com", user.getEmail());
	assertTrue(PasswordEncoder.passwordEncodeValidation("test123", user.getPassword()));
	assertFalse(user.isAdmin());
    }

    @Test
    void deleteShouldDeleteData() {
	System.out.println("DELETING USER...........");
	this.userRepository.save(new User("teste@email.com", PasswordEncoder.encode("test123"), false));
	Iterable<User> users = this.userRepository.findAll();
	User user = users.iterator().next();
	this.userRepository.delete(new User(user.getId()));
	Assertions.assertThat(this.userRepository.findById(user.getId())).isNull();
    }

}
