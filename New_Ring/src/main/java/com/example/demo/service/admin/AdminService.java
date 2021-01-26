package com.example.demo.service.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeUpdateRequest;
import com.example.demo.dto.PasswordRequest;
import com.example.demo.model.entity.Employee;
import com.example.demo.service.user.UserService;

@Service
public class AdminService extends UserService {

	/** 従業員情報全権取得(ページネーション) **/
	public Page<Employee> getAllWord(Pageable pageable) {
		return employeeRepository.findAll(pageable);
	}

	/** 登録画面から受け取った情報を employee にセット **/
	private Employee createEmployee(EmployeeRequest employeeRequest) {

		Employee employee = new Employee(employeeRequest);
		employee.setPassword(passwordEncoder.encode("password"));
		employee.setUsername(employeeRequest.getUsername());
		employee.setEmail(employeeRequest.getEmail());
		employee.setBirthday(employeeRequest.getBirthday());
		employee.setSex(employeeRequest.getSex());
		employee.setDepartment(employeeRequest.getDepartment());
		employee.setTelephone_Number(employeeRequest.getTelephone_Number());
		employee.setAddress(employeeRequest.getAddress());
		employee.setJoin_Date(employeeRequest.getJoin_Date());
		employee.setUpdated_at(now);

		return employee;
	}

	/** 登録画面から受け取った情報を employee にセット (管理者権限) **/
	private Employee createAdminEmployee(EmployeeRequest employeeRequest) {

		Employee employee = new Employee(employeeRequest);
		employee.setPassword(passwordEncoder.encode("password"));
		employee.setUsername(employeeRequest.getUsername());
		employee.setEmail(employeeRequest.getEmail());
		employee.setBirthday(employeeRequest.getBirthday());
		employee.setSex(employeeRequest.getSex());
		employee.setDepartment(employeeRequest.getDepartment());
		employee.setTelephone_Number(employeeRequest.getTelephone_Number());
		employee.setAddress(employeeRequest.getAddress());
		employee.setJoin_Date(employeeRequest.getJoin_Date());
		employee.setUpdated_at(now);
		employee.setAdmin(true);

		return employee;
	}

	/** 新規従業員の登録処理 **/
	public void create(EmployeeRequest employeeRequest) {
		employeeRepository.save(createEmployee(employeeRequest));
	}

	/** 新規従業員の登録処理 （管理者権限） */
	public void createAdmin(EmployeeRequest employeeRequest) {
		employeeRepository.save(createAdminEmployee(employeeRequest));
	}

	/** 管理者権限削除 **/
	public void removeAdmin(EmployeeUpdateRequest employeeUpdateRequest) {
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
		employee.setAdmin(false);
		employeeRepository.save(employee);
	}

	/** 管理者権限付与 **/
	public void addAdmin(EmployeeUpdateRequest employeeUpdateRequest) {
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
		employee.setAdmin(true);
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
