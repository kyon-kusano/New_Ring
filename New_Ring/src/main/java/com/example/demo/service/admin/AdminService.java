package com.example.demo.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeUpdateRequest;
import com.example.demo.dto.PasswordRequest;
import com.example.demo.model.entity.Employee;
import com.example.demo.service.user.UserService;

@Service
public class AdminService extends UserService {

	/** 従業員情報全権取得(ページネーション) **/
	public List<Employee> findAllList() {
		return employeeRepository.findAll();
	}

	/** 登録画面から受け取った情報を employee にセット **/
	private Employee createEmployee(EmployeeRequest employeeRequest) {

		Employee employee = new Employee(employeeRequest);
		employee.setPassword(passwordEncoder.encode("password"));
		employee.setUsername(employeeRequest.getUsername());
		employee.setEmail(employeeRequest.getEmail());
		employee.setBirthday(employeeRequest.getBirthday());
		employee.setSex(employeeRequest.getSex());
		employee.setDepartment(findDepartment(employeeRequest.getDepartment()));
		employee.setTelephone_Number(employeeRequest.getTelephone_Number());
		employee.setAddress(employeeRequest.getAddress());
		employee.setJoin_Date(employeeRequest.getJoin_Date());
		if (employeeRequest.isAuthority())
			employee.setAdmin(true);
		employee.setUpdated_at(now);

		return employee;
	}

	/** 新規従業員の登録処理 **/
	public void create(EmployeeRequest employeeRequest) {
		employeeRepository.save(createEmployee(employeeRequest));
	}

	/**
	 * 従業員の編集処理
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

	/** 従業員の削除 **/
	public void delete(Long id) {
		Employee employee = findById(id);
		employeeRepository.delete(employee);
	}

	/** パスワード変更 **/
	public void adminUpdatePassword(Long id, PasswordRequest passwordRequest) {

		Employee employee = findById(id);
		employee.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
		employee.setUpdated_at(now);
		employeeRepository.save(employee);
	}

}
