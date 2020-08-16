package br.com.crudrestapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {

	@Transient
	private final String REQUIRED_FIELD = "Required field";

	@NotNull
	@Email(message = "Invalid email address")
	@NotEmpty(message = REQUIRED_FIELD)
	@Column(unique = true)
	private String email;

	@NotNull
	@NotEmpty(message = REQUIRED_FIELD)
	private String password;

	public User() {
	}

	@Override
	public String toString() {
		return super.toString() + "\nUser [email=" + email + ", password=" + password + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}