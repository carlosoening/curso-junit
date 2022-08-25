package com.udemy.cursojunit.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.cursojunit.domain.User;
import com.udemy.cursojunit.repositories.UserRepository;
import com.udemy.cursojunit.services.UserService;
import com.udemy.cursojunit.services.exceptions.ObjectNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;
	
	@Override
	public User findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

}
