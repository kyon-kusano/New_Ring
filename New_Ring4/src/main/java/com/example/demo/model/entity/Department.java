package com.example.demo.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false, unique = true)
	private String name;

	@OneToMany(mappedBy = "department")
	private List<Employee> employees;

	protected Department() {

	}

	public Department(int id, String name) {
		this.id = id;
		this.name = name;
	}
//	public Department(String name) {
//		this.name = name;
//	}
}