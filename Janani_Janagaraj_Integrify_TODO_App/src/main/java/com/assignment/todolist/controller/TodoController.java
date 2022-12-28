package com.assignment.todolist.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.todolist.entity.Todo;
import com.assignment.todolist.entity.User;
import com.assignment.todolist.model.JwtResponse;
import com.assignment.todolist.model.UserModel;
import com.assignment.todolist.model.TodoModel;
import com.assignment.todolist.repository.TodoRepository;
import com.assignment.todolist.repository.UserRepository;
import com.assignment.todolist.service.TodoService;
import com.assignment.todolist.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;


@RestController
@RequestMapping("/api/v1")
public class TodoController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TodoRepository todoRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TodoService todoService;
	
	@Autowired
	private UserService userService;
	
	

	Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
	
	
	@PostMapping("/signup")
	public User registerUser(@RequestBody UserModel userModel) {
			LocalDateTime lt= LocalDateTime.now();
			User newUser = new User();
			newUser.setEmail(userModel.getEmail());
			newUser.setPassword(passwordEncoder.encode(userModel.getPassword()));
			newUser.setCreatedAt(lt);
			newUser.setUpdatedAt(lt);
			return userRepository.save(newUser);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> login(@RequestBody UserModel userModel) throws Exception{
		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userModel.getEmail(),userModel.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		}catch(BadCredentialsException e) {
			throw new Exception("Invalid credentials");
			
		}
		final String jwt = JWT.create().withSubject(userModel.getEmail()).withExpiresAt(new Date(System.currentTimeMillis() +10*60*1000))
    			.sign(algorithm);
		
		return ResponseEntity.ok(new JwtResponse(jwt));
	}
	
	
	
	@PutMapping("/changePassword")
	public User changePassword(@RequestBody UserModel userModel) {
		LocalDateTime lt= LocalDateTime.now();
		User theUser = new User();
		theUser = userService.findByEmail(userModel.getEmail());
		theUser.setUpdatedAt(lt);
		theUser.setPassword(userModel.getPassword());
		userService.save(theUser);
		
		return theUser;
	}
	
	
	@GetMapping("/todos")
	public List<Todo> getToDoList(@RequestParam(value="status",required=false) List<String> status) {
		List<Todo> foundTodos = new ArrayList<>();
		System.out.println("check status" +status);
		if(status != null) {
			for(int i=0;i<status.size();i++) {
				Todo theTodo = todoService.findByStatus(status.get(i));
				foundTodos.add(theTodo);
			}
			
			return foundTodos;
		}else {

			return todoService.findAll();
		}
	}
	
	@PostMapping("/todos")
	public Todo postToDoList(@RequestBody TodoModel todoModel) {
		Todo theTodo = new Todo();
		LocalDateTime lt= LocalDateTime.now();
		theTodo.setName(todoModel.getName());
		theTodo.setTododesc(todoModel.getTododesc());
		theTodo.setUser(userService.findById(todoModel.getUser_id()));
		theTodo.setStatus(todoModel.getStatus());
		theTodo.setCreatedAt(lt);
		theTodo.setUpdatedAt(lt);
		theTodo.setId(0L);
		todoService.save(theTodo);
		return theTodo;
	}
	
	@PutMapping("/todos/{todoId}")
	public Todo updateToDo(@PathVariable long todoId,@RequestBody TodoModel todoModel) {
		Todo theTodo = todoService.findById(todoId);
		if(theTodo == null) {
			throw new RuntimeException("Employee id not found - "+ todoId);
		}
		if(todoModel.getName() != null) {
			theTodo.setName(todoModel.getName());
		}
		if(todoModel.getTododesc() != null) {
			theTodo.setTododesc(todoModel.getTododesc());
		}
		if(userService.findById(todoModel.getUser_id()) != null) {
			theTodo.setUser(userService.findById(todoModel.getUser_id()));
		}
		if(todoModel.getStatus() != null) {
			theTodo.setStatus(todoModel.getStatus());
		}

		LocalDateTime lt= LocalDateTime.now();
		theTodo.setUpdatedAt(lt);
		todoService.save(theTodo);
		return theTodo;
	}
	
	
	@DeleteMapping("/todos/{todoId}")
	public String deleteTodo(@PathVariable long todoId) {
		Todo theTodo = todoService.findById(todoId);
		if(theTodo == null) {
			throw new RuntimeException("Employee id not found - "+ todoId);
		}
		todoService.deleteById(todoId);
		
		return "deleted todo id - " + todoId;
	}
	

}
