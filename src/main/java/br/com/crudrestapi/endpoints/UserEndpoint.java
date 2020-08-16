package br.com.crudrestapi.endpoints;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> index(Pageable pageable) {
		return new ResponseEntity<>(this.userRepository.findAll(pageable), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> userById(@PathVariable(value = "id") long id) {
		User user = getUser(id);
		System.out.println(user == null);
		userExists(user, id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> saveUser(@Valid @RequestBody User user) {
		return new ResponseEntity<>(this.userRepository.save(user), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		User userValidation = this.getUser(user.getId());
		userExists(userValidation, user.getId());
		return new ResponseEntity<>(this.userRepository.save(user), HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteUser(@RequestBody User user) {
		User userValidation = this.getUser(user.getId());
		userExists(userValidation, user.getId());
		this.userRepository.delete(user);
		return new ResponseEntity<>(HttpStatus.OK);
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
