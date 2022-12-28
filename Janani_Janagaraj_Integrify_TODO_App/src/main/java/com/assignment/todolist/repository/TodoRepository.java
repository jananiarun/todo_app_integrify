package com.assignment.todolist.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.todolist.entity.Todo;
import com.assignment.todolist.entity.User;

public interface TodoRepository extends JpaRepository<Todo, Long>{
	Optional<Todo>findByStatus(String status);
}
