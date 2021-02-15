package com.example.demo.controller;

import java.util.List;

public interface EmployeeRepositoryCustom {

//	public Employee findById(Long id);

//	public List<Employee> searchDepartment(int startIndex, int pageSize, String department, String column);

//	public int findDepartmentCnt(String department, String column);
//
//	public List<Employee> findListPaging(int startIndex, int pageSize);
//
//	public int findAllCnt();
//
//	public List<Employee> searchKeyword(int startIndex, int pageSize, String keyword);
//
//	public int findAllCnt(String keyword);
//
	public List<Department> findAllDepartments();

	public Department findDepartment(Long id);

}
