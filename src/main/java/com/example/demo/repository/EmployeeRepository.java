package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	public Employee findByEmail(String email);

	public List<Employee> findAll();

}
