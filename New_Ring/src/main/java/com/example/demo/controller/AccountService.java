package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

	@Autowired
	private AccountRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private final EmployeeRepositoryCustomImpl employeeRepositoryCustomImpl;

	private List<Department> result1;


	private List<Employee> result;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		if (email == null || "".equals(email)) {
			throw new UsernameNotFoundException("Username is empty");
		}

		Employee user = repository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User not found: " + email);
		}

		return user;
	}

	// adminを登録するメソッド
		@Transactional
		public void registerAdmin(String username, String password, String email, Date birthday, int sex, Department department, String telephone_Number,
				   String address, Date join_Date, Date updated_at, Date deleted_at) {
			Employee user = new Employee(username, passwordEncoder.encode(password), email, birthday, sex, department, telephone_Number,
										address, join_Date, updated_at, deleted_at);
			user.setAdmin(true);
			repository.save(user);
		}

	public List<Employee> findAllList() {
		result = repository.findAll();
		return result;
	}

	public List<Department> findAllDepartment() {
		result1 =employeeRepositoryCustomImpl.findAllDepartments() ;
		return result1;
	}

	public Department findDepartment(Long id) {
		return employeeRepositoryCustomImpl.findDepartment(id);

	}



	/**
	 * ユーザー情報新規登録
	 * @param user ユーザー情報
	 */
	public void create(AccountForm accountForm) {
		repository.save(CreateUser(accountForm));
	}

	/**
	 * ユーザーTBLエンティティの生成
	 * @param userRequest ユーザー情報リクエストデータ
	 * @return ユーザーTBLエンティティ
	 */
	private Employee CreateUser(AccountForm accountForm) {
		Date now = new Date();

		Employee user = new Employee();
		user.setUsername(accountForm.getUsername());
		user.setSex(accountForm.getSex());
		user.setBirthday(accountForm.getBirthday());
		user.setAddress(accountForm.getAddress());
		user.setEmail(accountForm.getEmail());
		user.setTelephone_Number(accountForm.getTelephone_Number());
		user.setJoin_Date(now);
		user.setDepartment(employeeRepositoryCustomImpl.findDepartment(accountForm.getDepartment()) );
		user.setPassword(accountForm.getPassword());
		user.setAuthorities(accountForm.getAuthorities());
		user.setAdmin(true);

		return user;
	}

}
