package br.com.crudrestapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.crudrestapi.entity.User;
import br.com.crudrestapi.repository.UserRepository;

@Component
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository) {
	this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	User user = Optional.ofNullable(userRepository.findByEmail(email))
		.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	List<GrantedAuthority> authorityAdmin = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
	List<GrantedAuthority> authorityUser = AuthorityUtils.createAuthorityList("ROLE_USER");
	org.springframework.security.core.userdetails.User sbUser = new org.springframework.security.core.userdetails.User(
		user.getEmail(), user.getPassword(), user.isAdmin() ? authorityAdmin : authorityUser);
	return sbUser;
    }

}