package com.udemy.cursojunit.services;

import java.util.List;

import com.udemy.cursojunit.domain.User;
import com.udemy.cursojunit.domain.dto.UserDTO;

public interface UserService {

	User findById(Integer id);
	
	List<User> findAll();
	
	User create(UserDTO dto);
	
	User update(UserDTO dto);
	
	void delete(Integer id);
}
