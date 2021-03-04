package com.example.demo.repository;

import com.example.demo.model.entity.Employee;

import java.util.List;

public interface EmployeeRepositoryCustom {


    List<Employee> searchDepartment(int startIndex, int pageSize, String department, String column);

    int findDepartmentCnt(String department, String column);

    List<Employee> findListPaging(int startIndex, int pageSize);

    int findAllCnt();

    List<Employee> searchKeyword(int startIndex, int pageSize, String keyword);

    int findAllCnt(String keyword);


}
