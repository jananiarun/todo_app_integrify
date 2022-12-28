package com.assignment.todolist.service;

import java.util.List;

import com.assignment.todolist.entity.Todo;


public interface TodoService {
	
	public List<Todo> findAll();
	
	public Todo findById(long theId);
	
	public Todo findByStatus(String status);
	
	public void save(Todo theEmployee);
	
	public void deleteById(long theId);
}
