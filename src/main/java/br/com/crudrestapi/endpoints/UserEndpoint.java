package br.com.crudrestapi.endpoints;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.crudrestapi.entity.User;
import br.com.crudrestapi.error.ResourceNotFoundException;
import br.com.crudrestapi.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserEndpoint {

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public List<User> index() {
		return this.userRepository.findAll();
	}

	@GetMapping("/{id}")
	public User userById(@PathVariable(value = "id") long id) {
		User user = getUser(id);
		System.out.println(user==null);
		userExists(user, id);
		return user;
	}

	@PostMapping
	public User saveUser(@Valid @RequestBody User user) { 
		return this.userRepository.save(user);
	}

	@PutMapping
	public User updateUser(@RequestBody User user) {
		User userValidation = this.getUser(user.getId());
		userExists(userValidation, user.getId());
		return this.userRepository.save(user);
	}

	@DeleteMapping
	public void deleteUser(@RequestBody User user) {
		User userValidation = this.getUser(user.getId());
		userExists(userValidation, user.getId());
		this.userRepository.delete(user);
	}

	private User getUser(long id) {
		return this.userRepository.findById(id);
	}

	private void userExists(User user, long id) {
		if (user == null) {
			throw new ResourceNotFoundException("User not found with id: " + id);
		}
	}

}
