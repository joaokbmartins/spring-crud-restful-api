package br.com.crudrestapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.crudrestapi.entity.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    public User findById(long id);

    public User findByEmail(String email);
}
