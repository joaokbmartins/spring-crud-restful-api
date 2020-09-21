package br.com.crudrestapi.endpoints;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
import br.com.crudrestapi.util.PasswordEncoder;

@RestController
@RequestMapping("/api/v1")
public class UserEndpoint {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("internal/users")
    public ResponseEntity<?> index(Pageable pageable) {
	return new ResponseEntity<>(this.userRepository.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("internal/users/{id}")
    public
	    ResponseEntity<?>
	    userById(@PathVariable(value = "id") long id, @AuthenticationPrincipal UserDetails userDetails) {
	User user = getUser(id);
	userExists(user, id);
	return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("admin/users")
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
	user.setPassword(PasswordEncoder.encode(user.getPassword()));
	return new ResponseEntity<>(this.userRepository.save(user), HttpStatus.CREATED);
    }

    @PutMapping("admin/users")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
	User userValidation = this.getUser(user.getId());
	userExists(userValidation, user.getId());
	user.setPassword(PasswordEncoder.encode(user.getPassword()));
	return new ResponseEntity<>(this.userRepository.save(user), HttpStatus.OK);
    }

    @DeleteMapping("admin/users")
//	 @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@RequestBody User user, @AuthenticationPrincipal UserDetails userDetails) {
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
