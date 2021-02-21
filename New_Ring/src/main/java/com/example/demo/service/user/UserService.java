package com.example.demo.service.user;

import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EmployeeUpdateRequest;
import com.example.demo.dto.PasswordRequest;
import com.example.demo.model.entity.Employee;
import com.example.demo.service.base.BaseService;

@Service
public class UserService extends BaseService {

	/** id に紐づく従業員情報の取得 **/

	/**
	 * 従業員の編集処理（USER, ADMIN共通処理）
	 **/
	public void update(EmployeeUpdateRequest employeeUpdateRequest) {
		Employee employee = findById(employeeUpdateRequest.getId());

		employee.setUsername(employeeUpdateRequest.getUsername());
		employee.setEmail(employeeUpdateRequest.getEmail());
		employee.setBirthday(employeeUpdateRequest.getBirthday());
		employee.setSex(employeeUpdateRequest.getSex());
		employee.setAddress(employeeUpdateRequest.getAddress());
		employee.setDepartment(findDepartment(employeeUpdateRequest.getDepartment()));
		employee.setTelephone_Number(employeeUpdateRequest.getTelephone_Number());
		employee.setJoin_Date(employeeUpdateRequest.getJoin_Date());
		employee.setUpdated_at(now);
		if (employeeUpdateRequest.isAuthority() == true) {
			employee.setAdmin(true);
		} else if (employeeUpdateRequest.isAuthority() == false) {
			employee.setAdmin(false);
		}
		employeeRepository.save(employee);
	}

	/** パスワード変更 **/
	public void updatePassword(PasswordRequest passwordRequest) {

		Employee loggedEmployee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long id = loggedEmployee.getId();
		Employee employee = findById(id);
		employee.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
		employee.setUpdated_at(new Date());
		employeeRepository.save(employee);
	}

}
