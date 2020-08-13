package br.com.crudrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.crudrestapi.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	
	public User findById(int id);
	
}
