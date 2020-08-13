package br.com.crudrestapi.rest;

import java.util.List;

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
import br.com.crudrestapi.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserRest {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/users")
	public List<User> index() {
		System.out.println(34123);
		return this.userRepository.findAll();
	}

	@GetMapping("/users/{id}")
	public User userById(@PathVariable(value = "id") int id) {
		return this.userRepository.findById(id);
	}

	@PostMapping("/users")
	public User saveUser(@RequestBody User user) {
		return this.userRepository.save(user);
	}

	@PutMapping("/users/{id}")
	public User updateUser(@RequestBody User user) {
		return this.userRepository.save(user);
	}

	@DeleteMapping("/users/")
	public void deleteUser(@RequestBody User user) {
		this.userRepository.delete(user);
	}

}
