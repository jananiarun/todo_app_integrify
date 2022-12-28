package com.assignment.todolist.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.todolist.entity.User;
import com.assignment.todolist.repository.TodoRepository;
import com.assignment.todolist.repository.UserRepository;

@Service
public class UserServiceImp implements UserService {
	
	private UserRepository userRepository;
	
	
	@Autowired
	public UserServiceImp(UserRepository theUserRepository) {
		userRepository = theUserRepository;
	}

	@Override
	public List<User> findAll() {	
		return userRepository.findAll();
	}

	@Override
	public User findById(long theId) {
		Optional<User> result = userRepository.findById(theId);
		
		User theUser =null;
		if(result.isPresent()) {
			theUser =  result.get();
		}else {
			throw new RuntimeException("Did not find user id - "+theId);
		}
		return theUser;
	}
	
	@Override
	public User findByEmail(String email) {
		Optional<User> result = userRepository.findByEmail(email);
		User theUser =null;
		if(result.isPresent()) {
			theUser =  result.get();
		}else {
			throw new RuntimeException("Did not find user email - "+email);
		}
		return theUser;
	}

	@Override
	public void save(User theUser) {
		userRepository.save(theUser);
	}

	@Override
	public void deleteById(long theId) {
		userRepository.deleteById(theId);
	}

}
