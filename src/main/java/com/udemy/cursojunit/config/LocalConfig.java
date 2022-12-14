package com.udemy.cursojunit.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.udemy.cursojunit.domain.User;
import com.udemy.cursojunit.repositories.UserRepository;

@Configuration
@Profile("local")
public class LocalConfig {

	@Autowired
	private UserRepository repository;
	
	@Bean
	public void startDB() {
		User u1 = new User(null, "Carlos", "carlos@email.com", "123");
		User u2 = new User(null, "Jose", "jose@email.com", "123");
		repository.saveAll(List.of(u1, u2));
	}
}
