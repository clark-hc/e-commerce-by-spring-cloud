package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.entity.User;
import com.example.userservice.exception.ResourceNotFoundException;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.impls.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserServiceImplTest {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private UserRepository userRepository;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@AfterEach
	void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	void testCreateUser() {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("testuser");
		userDTO.setPassword("password");

		UserDTO createdUserDTO = userService.createUser(userDTO);

		assertNotNull(createdUserDTO.getId());
		assertEquals(userDTO.getUsername(), createdUserDTO.getUsername());
	}

	@Test
	void testGetUserById() {
		User user = new User();
		user.setUsername("testuser");
		user.setPassword("password");
		User savedUser = userRepository.save(user);

		UserDTO userDTO = userService.getUserById(savedUser.getId());

		assertNotNull(userDTO);
		assertEquals(savedUser.getId(), userDTO.getId());
		assertEquals(savedUser.getUsername(), userDTO.getUsername());
	}

	@Test
	void testGetUserByIdNotFound() {
		assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(999L));
	}

	@Test
	void testGetAllUsers() {
		User user1 = new User();
		user1.setUsername("testuser1");
		user1.setPassword("password1");
		userRepository.save(user1);

		User user2 = new User();
		user2.setUsername("testuser2");
		user2.setPassword("password2");
		userRepository.save(user2);

		List<UserDTO> userDTOs = userService.getAllUsers();

		assertEquals(2, userDTOs.size());
	}

	@Test
	void testUpdateUser() {
		User user = new User();
		user.setUsername("testuser");
		user.setPassword("password");
		User savedUser = userRepository.save(user);

		UserDTO updatedUserDTO = new UserDTO();
		updatedUserDTO.setUsername("updateduser");
		updatedUserDTO.setPassword("newpassword");

		UserDTO result = userService.updateUser(savedUser.getId(), updatedUserDTO);

		assertNotNull(result.getId());
		assertEquals(updatedUserDTO.getUsername(), result.getUsername());
	}

	@Test
	void testDeleteUser() {
		User user = new User();
		user.setUsername("testuser");
		user.setPassword("password");
		User savedUser = userRepository.save(user);

		userService.deleteUser(savedUser.getId());

		assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(savedUser.getId()));
	}

	@Test
	void testUpdatePassword() {
		User user = new User();
		user.setUsername("testuser");
		user.setPassword("password");
		User savedUser = userRepository.save(user);

		String newPassword = "newpassword";
		userService.updatePassword(savedUser.getId(), newPassword);

		User updatedUser = userRepository.findById(savedUser.getId()).orElseThrow();
		assertTrue(passwordEncoder.matches(newPassword, updatedUser.getPassword()));
	}
}