package com.example.userservice.service.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.entity.User;
import com.example.userservice.exception.ResourceNotFoundException;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	@Transactional(readOnly = false)
	public UserDTO createUser(UserDTO userDTO) {
		User user = mapToEntity(userDTO);
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		User savedUser = userRepository.save(user);
		return mapToDTO(savedUser);
	}

	@Override
	public UserDTO getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
		return mapToDTO(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = false)
	public UserDTO updateUser(Long id, UserDTO userDTO) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

		existingUser.setUsername(userDTO.getUsername());

		// Only update the password if a new password is provided
		if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
			String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
			existingUser.setPassword(encodedPassword);
		}

		User updatedUser = userRepository.save(existingUser);
		return mapToDTO(updatedUser);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
		userRepository.delete(user);
	}

	@Override
	@Transactional(readOnly = false)
	public void updatePassword(Long id, String newPassword) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

		String encodedPassword = passwordEncoder.encode(newPassword);
		userRepository.updatePassword(id, encodedPassword);
	}

	private UserDTO mapToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setUsername(user.getUsername());
		// just for test, do not print password in production environment
		userDTO.setPassword(user.getPassword());
		return userDTO;
	}

	private User mapToEntity(UserDTO userDTO) {
		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		return user;
	}
}