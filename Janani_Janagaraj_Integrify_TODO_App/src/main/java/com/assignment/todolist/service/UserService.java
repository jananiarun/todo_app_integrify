package com.assignment.todolist.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.assignment.todolist.entity.User; 


public interface UserService {
	public List<User> findAll();
	
	public User findById(long theId);
	
	public User findByEmail(String email);
	
	public void save(User theEmployee);
	
	public void deleteById(long theId);
}
