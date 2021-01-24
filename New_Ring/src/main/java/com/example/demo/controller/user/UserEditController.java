package com.example.demo.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.bean.selectBean;
import com.example.demo.dto.EmployeeUpdateRequest;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.base.BaseService;

@Controller
@RequestMapping("/edit/{id}")
public class UserEditController implements selectBean {

	@Autowired
	BaseService baseService;
	@Autowired
	EmployeeRepository employeeRepository;

	/** ログインユーザ 情報変更画面 **/
	@GetMapping
	public String myEdit(Model model, Model stringModel) {

		Employee loggedAccount = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = loggedAccount.getEmail();
		Employee employee = employeeRepository.findByEmail(email);

		stringModel.addAttribute("departments", getDepartments);
		stringModel.addAttribute("sexes", getSex);

		model.addAttribute("employeeUpdateRequest", employee);
		return "update_form";
	}

	/**
	 * ログインユーザ 編集処理
	 **/
	@PostMapping
	public String Update(@Validated @ModelAttribute EmployeeUpdateRequest employeeUpdateRequest, BindingResult result,
			Model model, Model stringModel) {
		if (result.hasErrors()) {

			stringModel.addAttribute("departments", getDepartments);
			stringModel.addAttribute("sexes", getSex);

			return "update_form";
		}

		baseService.update(employeeUpdateRequest);
		return "redirect:/top";
	}

}
