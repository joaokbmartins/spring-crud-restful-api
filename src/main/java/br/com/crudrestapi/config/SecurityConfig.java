package br.com.crudrestapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.crudrestapi.service.CustomUserDetailService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http.authorizeRequests()
		.antMatchers("/**/internal/**")
		.hasRole("USER")
		.antMatchers("/**/admin/**")
		.hasRole("ADMIN")
		.and()
		.httpBasic()
		.and()
		.csrf()
		.disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(this.customUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication() 
//			.withUser("roberto").password("{noop}teste").roles("USER")
//			.and()
//			.withUser("admin").password("{noop}teste").roles("USER", "ADMIN");
//		
//	}
}
