package com.example.demo.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Employee, Long>{

	public Employee findByEmail(String email);

	public List<Employee> findAll();

}
