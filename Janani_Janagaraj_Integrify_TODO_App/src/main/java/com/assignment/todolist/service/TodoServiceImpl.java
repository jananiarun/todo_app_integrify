package com.assignment.todolist.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.assignment.todolist.entity.Todo;
import com.assignment.todolist.entity.User;
import com.assignment.todolist.repository.TodoRepository;


@Service
public class TodoServiceImpl implements TodoService {

	private TodoRepository todoRepository;
	
	
	public TodoServiceImpl(TodoRepository theTodoRepository) {
		todoRepository = theTodoRepository;
	}

	@Override
	public List<Todo> findAll() {
		return todoRepository.findAll();
	}

	@Override
	public Todo findById(long theId) {
		Optional<Todo> result = todoRepository.findById(theId);
		Todo theToDo = null;
		if(result.isPresent()) {
			theToDo = result.get();
		}else {
			throw new RuntimeException("Did not find todo id - "+theId);
		}
		return theToDo;
	}
	
	@Override
	public Todo findByStatus(String status) {
		Optional<Todo> result = todoRepository.findByStatus(status);
		Todo theTodo =null;
		if(result.isPresent()) {
			theTodo =  result.get();
		}else {
			throw new RuntimeException("Did not find todo status - "+status);
		}
		return theTodo;
	}

	@Override
	public void save(Todo theToDo) {
		todoRepository.save(theToDo);
	}

	@Override
	public void deleteById(long theId) {
		todoRepository.deleteById(theId);
	}

}
