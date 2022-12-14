package com.udemy.cursojunit.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.udemy.cursojunit.domain.User;
import com.udemy.cursojunit.domain.dto.UserDTO;
import com.udemy.cursojunit.repositories.UserRepository;
import com.udemy.cursojunit.services.exceptions.DataIntegrityViolationException;
import com.udemy.cursojunit.services.exceptions.ObjectNotFoundException;

@SpringBootTest
class UserServiceImplTest {

	private static final String EMAIL_JA_CADASTRADO_NO_SISTEMA = "E-mail ja cadastrado no sistema";

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
		assertEquals(PASSWORD, response.getPassword());
	}

	@Test
	void whenFindByIdThenReturnAnObjectNotFoundException() {
		when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));
		
		try {
			service.findById(ID);
		} catch (Exception e) {
			assertEquals(ObjectNotFoundException.class, e.getClass());
			assertEquals(OBJETO_NAO_ENCONTRADO, e.getMessage());
		}
	}
	
	@Test
	void whenFindAllThenReturnAListOfUsers() {
		when(repository.findAll()).thenReturn(List.of(user));
		
		List<User> response = service.findAll();
		
		assertNotNull(response);
		assertEquals(1, response.size());
		assertEquals(User.class, response.get(0).getClass());
		assertEquals(ID, response.get(0).getId());
		assertEquals(NAME, response.get(0).getName());
		assertEquals(EMAIL, response.get(0).getEmail());
		assertEquals(PASSWORD, response.get(0).getPassword());
	}

	@Test
	void whenCreateThenReturnSuccess() {
		when(repository.save(any())).thenReturn(user);
		
		User response = service.create(userDTO);
		
		assertNotNull(response);
		assertEquals(User.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getName());
		assertEquals(EMAIL, response.getEmail());
		assertEquals(PASSWORD, response.getPassword());
	}
	
	@Test
	void whenCreateThenReturnADataIntegrityViolationException() {
		when(repository.findByEmail(anyString())).thenReturn(optionalUser);
		
		try {
			optionalUser.get().setId(2);
			service.create(userDTO);
		} catch (Exception e) {
			assertEquals(DataIntegrityViolationException.class, e.getClass());
			assertEquals(EMAIL_JA_CADASTRADO_NO_SISTEMA, e.getMessage());
		}
	}

	@Test
	void whenUpdateThenReturnSuccess() {
		when(repository.save(any())).thenReturn(user);
		
		User response = service.update(userDTO);
		
		assertNotNull(response);
		assertEquals(User.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getName());
		assertEquals(EMAIL, response.getEmail());
		assertEquals(PASSWORD, response.getPassword());
	}
	
	@Test
	void whenUpdateThenReturnADataIntegrityViolationException() {
		when(repository.findByEmail(anyString())).thenReturn(optionalUser);
		
		try {
			optionalUser.get().setId(2);
			service.update(userDTO);
		} catch (Exception e) {
			assertEquals(DataIntegrityViolationException.class, e.getClass());
			assertEquals(EMAIL_JA_CADASTRADO_NO_SISTEMA, e.getMessage());
		}
	}

	@Test
	void deleteWithSuccess() {
		when(repository.findById(anyInt())).thenReturn(optionalUser);
		doNothing().when(repository).deleteById(anyInt());
		service.delete(ID);
		verify(repository, times(1)).deleteById(anyInt());
	}

	@Test
	void deleteWithObjectNotFoundException() {
		when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));
		
		try {
			service.delete(ID);
		} catch (Exception e) {
			assertEquals(ObjectNotFoundException.class, e.getClass());
			assertEquals(OBJETO_NAO_ENCONTRADO, e.getMessage());
		}
	}
	
	private void startUser() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
		optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
	}
}
