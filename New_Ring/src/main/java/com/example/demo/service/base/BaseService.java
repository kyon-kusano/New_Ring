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
import com.example.demo.model.entity.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class BaseService extends selectBean implements UserDetailsService {

	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Autowired
	protected EmployeeRepository employeeRepository;

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
			String department, String telephone_Number, String address, Date join_Date, Date updated_at,
			Date deleted_at) {
		Employee user = new Employee(username, passwordEncoder.encode(password), email, birthday, sex, department,
				telephone_Number, address, join_Date, updated_at, deleted_at);
		user.setAdmin(true);
		employeeRepository.save(user);
	}

	/** 従業員情報全権取得 起動時テストユーザ登録で使用 **/
	public List<Employee> findAllList() {
		return employeeRepository.findAll();
	}

}
