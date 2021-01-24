package com.example.demo.service.user;

import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PasswordRequest;
import com.example.demo.entity.Employee;
import com.example.demo.service.base.CommonService;

@Service
public class UserService extends CommonService {

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
