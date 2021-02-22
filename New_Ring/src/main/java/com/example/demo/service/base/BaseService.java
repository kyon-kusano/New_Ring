package com.example.demo.service.base;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.bean.selectBean;
import com.example.demo.model.entity.Address;
import com.example.demo.model.entity.Department;
import com.example.demo.model.entity.Details;
import com.example.demo.model.entity.Employee;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.DetailsRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.EmployeeRepositoryCustomImpl;

@Service
public class BaseService extends selectBean implements UserDetailsService {

	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Autowired
	protected EmployeeRepository employeeRepository;
	@Autowired
	protected DepartmentRepository departmentRepository;
	@Autowired
	protected AddressRepository addressRepository;
	@Autowired
	protected DetailsRepository detailsRepository;
	@Autowired
	protected EmployeeRepositoryCustomImpl employeeRepositoryCustomImpl;

	/** emailでログイン処理 **/
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		if (email == null || "".equals(email)) {
			throw new UsernameNotFoundException("Username is empty");
		}
		Employee user = employeeRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User not found: " + email);
		}
		return user;
	}

	/** 初回起動時、テストユーザ（admin）追加 **/
	@Transactional
	public void registerAdmin(String username, String password, String email, Date birthday, String sex,
			Department department, String telephone_Number, Address address, Details details, Date join_Date,
			Date updated_at, Date deleted_at) {
		Employee employee = new Employee(username, passwordEncoder.encode(password), email, birthday, sex, department,
				telephone_Number, address, details, join_Date, updated_at, deleted_at);
		employee.setAdmin(true);

		employeeRepository.save(employee);
	}

	public Address createAddress(String post_Number, String address1, String address2) {
		Address address = new Address(post_Number, address1, address2);
		return address;
	}

	public Details createDetails(String image, String comment) {
		Details details = new Details(image, comment);
		return details;
	}

	public void createDepartment(String name) {
		Department department = new Department(name);
		departmentRepository.save(department);
	}

	/** 従業員情報全権取得 起動時テストユーザ登録で使用 **/
	public List<Employee> findAllList() {
		return employeeRepository.findAll();
	}

	/** 部署テーブル全権取得 起動時部署名登録 **/
	public List<Department> findAllDepartments() {
		return departmentRepository.findAll();
	}

	public Department findDepartment(int id) {
		return departmentRepository.findById(id);
	}

	/** id に紐づく従業員情報の取得 **/
	public Employee findById(Long id) {
		return employeeRepository.findById(id).get();
	}

	public Employee findByEmail(String Email) {
		return employeeRepository.findByEmail(Email);
	}

	public Details findByDetails(Long id) {
		return detailsRepository.findById(id).get();
	}

}
