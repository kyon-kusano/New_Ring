package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.entity.Department;
import com.example.demo.model.entity.Employee;

public interface EmployeeRepositoryCustom {

	public Employee findById(Long id);

	public List<Employee> searchDepartment(int startIndex, int pageSize, String department, String column);

	public int findDepartmentCnt(String department, String column);

	public List<Employee> findListPaging(int startIndex, int pageSize);

	public int findAllCnt();

	public List<Employee> searchKeyword(int startIndex, int pageSize, String keyword);

	public int findAllCnt(String keyword);

	public List<Department> findAllDepartments();

	public Department findDepartment(int id);

}
