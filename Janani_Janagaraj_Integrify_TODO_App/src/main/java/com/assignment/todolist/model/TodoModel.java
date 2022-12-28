package com.assignment.todolist.model;


import com.assignment.todolist.entity.User;

import lombok.Data;

@Data
public class TodoModel {

	private String name;
	private String tododesc;
	private long user_id;
	private String status;
}
