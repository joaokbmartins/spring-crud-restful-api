package br.com.crudrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.crudrestapi.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	public User findById(long id);
}
