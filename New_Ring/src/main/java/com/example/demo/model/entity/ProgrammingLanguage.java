package com.example.demo.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

@Data
@Entity
public class ProgrammingLanguage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@ManyToMany( mappedBy = "programming_Language")
	private List<Employee> employees;

	public ProgrammingLanguage() {

	}

	public ProgrammingLanguage(String name) {
		this.name = name;
	}

}