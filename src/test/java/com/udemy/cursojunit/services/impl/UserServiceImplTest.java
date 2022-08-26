package com.udemy.cursojunit.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.udemy.cursojunit.domain.User;
import com.udemy.cursojunit.domain.dto.UserDTO;
import com.udemy.cursojunit.repositories.UserRepository;
import com.udemy.cursojunit.services.exceptions.ObjectNotFoundException;

class UserServiceImplTest {

	private static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";

	private static final Integer ID = 1;

	private static final String NAME = "Valdir";
	
	private static final String EMAIL = "valdir@mail.com";
	
	private static final String PASSWORD = "123";
	
	@InjectMocks
	private UserServiceImpl service;
	
	@Mock
	private UserRepository repository;
	
	@Mock
	private ModelMapper mapper;
	
	private User user;
	
	private UserDTO userDTO;
	
	private Optional<User> optionalUser;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}
	
	@Test
	void whenFindByIdThenReturnAnUserInstance() {
		when(repository.findById(anyInt())).thenReturn(optionalUser);
		User response = service.findById(ID);
		
		assertNotNull(response);
		assertEquals(User.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getName());
		assertEquals(EMAIL, response.getEmail());
	}

	@Test
	void whenFindByIdThenReturnAnObjectNotFoundException() {
		when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));
		
		try {
			service.findById(ID);
		} catch (Exception e) {
			assertEquals(ObjectNotFoundException.class, e.getClass());
			assertEquals("Objetao não encontrado", e.getMessage());
		}
	}
	
	@Test
	void testFindAll() {
	}

	@Test
	void testCreate() {
	}

	@Test
	void testUpdate() {
	}

	@Test
	void testDelete() {
	}

	private void startUser() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
		optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
	}
}
