package com.example.demo.repository;

import com.example.demo.model.entity.Employee;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByEmail(String email);

    Optional<Employee> findById(@NotNull Long id);


}
