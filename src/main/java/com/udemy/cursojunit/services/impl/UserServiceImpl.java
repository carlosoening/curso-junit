package com.udemy.cursojunit.services.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.cursojunit.domain.User;
import com.udemy.cursojunit.domain.dto.UserDTO;
import com.udemy.cursojunit.repositories.UserRepository;
import com.udemy.cursojunit.services.UserService;
import com.udemy.cursojunit.services.exceptions.DataIntegrityViolationException;
import com.udemy.cursojunit.services.exceptions.ObjectNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public User findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}
	
	@Override
	public User create(UserDTO dto) {
		findByEmail(dto);
		return repository.save(mapper.map(dto, User.class));
	}
	
	@Override
	public User update(UserDTO dto) {
		findByEmail(dto);
		return repository.save(mapper.map(dto, User.class));
	}
	
	@Override
	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);
	}
	
	private void findByEmail(UserDTO dto) {
		Optional<User> user = repository.findByEmail(dto.getEmail());
		if (user.isPresent() && !user.get().getId().equals(dto.getId())) {
			throw new DataIntegrityViolationException("E-mail ja cadastrado no sistema");
		}
	}
}
