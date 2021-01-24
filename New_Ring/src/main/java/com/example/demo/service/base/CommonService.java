package com.example.demo.service.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.bean.selectBean;
import com.example.demo.dto.EmployeeUpdateRequest;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class CommonService implements selectBean {

	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Autowired
	protected EmployeeRepository employeeRepository;

	/** 従業員情報全権取得 起動時テストユーザ登録で使用 **/
	public List<Employee> findAllList() {
		return employeeRepository.findAll();
	}

	/** 従業員情報全権取得(ページネーション) **/
	public Page<Employee> getAllWord(Pageable pageable) {
		return employeeRepository.findAll(pageable);
	}

	/** id に紐づく従業員情報の取得 **/
	public Employee findById(Long id) {
		return employeeRepository.findById(id).get();
	}

	/**
	 * 従業員の編集処理（USER, ADMIN共通処理）
	 **/
	public void update(EmployeeUpdateRequest employeeUpdateRequest) {
		Employee employee = findById(employeeUpdateRequest.getId());

		employee.setUsername(employeeUpdateRequest.getUsername());
		employee.setEmail(employeeUpdateRequest.getEmail());
		employee.setBirthday(employeeUpdateRequest.getBirthday());
		employee.setSex(employeeUpdateRequest.getSex());
		employee.setDepartment(employeeUpdateRequest.getDepartment());
		employee.setTelephone_Number(employeeUpdateRequest.getTelephone_Number());
		employee.setAddress(employeeUpdateRequest.getAddress());
		employee.setJoin_Date(employeeUpdateRequest.getJoin_Date());
		employee.setUpdated_at(now);
		employeeRepository.save(employee);
	}

}
