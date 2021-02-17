package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

	public List<Department> findAll();

	public Department findById(int id);
}
