package com.assignment.todolist.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="todolist_tdlist")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
	
	public enum ChoiceStatus  { NotStarted, OnGoing, Completed};
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "todo_id")
	private Long id;
	
	private String name;
	private String tododesc;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@CreationTimestamp
	private LocalDateTime CreatedAt;
	
	@LastModifiedBy
	private LocalDateTime UpdatedAt;
	
	
	@Column(name = "status")
	private String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String theStatus) {
		if(contains(theStatus)) {
			this.status= theStatus;
		}else {
			throw new RuntimeException("This status should be one of these [NotStarted, OnGoing, Completed] ");
		}	
		
	}
	
	public static boolean contains(String test) {

	    for (ChoiceStatus c : ChoiceStatus.values()) {
	        if (c.name().equals(test)) {
	            return true;
	        }
	    }

	    return false;
	}
	
	
	
	
}
