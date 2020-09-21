package br.com.crudrestapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "email", name = "uk_unique_email") })
public class User extends AbstractEntity {

    @Transient
    private final String REQUIRED_FIELD = "Required field";

    @NotNull
    @Email(message = "Invalid email address")
    @NotEmpty(message = REQUIRED_FIELD)
    private String email;

    @NotNull
    @NotEmpty(message = REQUIRED_FIELD)
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = REQUIRED_FIELD)
    @JsonProperty(required = true)
    @Column(nullable = false)
    private boolean admin;

    @Override
    public String toString() {
	return "User [id=" + this.getId() + " email=" + email + ", password=" + password + ", admin=" + admin + "]";
    }

    public User() {
    }

    public User(
	    @NotNull @Email(message = "Invalid email address") @NotEmpty(message = "Required field") String email,
	    @NotNull @NotEmpty(message = "Required field") String password,
	    @NotNull(message = "Required field") boolean admin) {
	super();
	this.email = email;
	this.password = password;
	this.admin = admin;
    }

    public User(long id) {
	this.setId(id);
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

    public boolean isAdmin() {
	return admin;
    }

    public void setAdmin(boolean admin) {
	this.admin = admin;
    }

}