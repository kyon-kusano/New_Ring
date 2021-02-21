package com.example.demo.controller.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.bean.selectBean;
import com.example.demo.dto.EmployeeUpdateRequest;
import com.example.demo.dto.PasswordRequest;
import com.example.demo.model.entity.Department;
import com.example.demo.model.entity.Employee;
import com.example.demo.service.user.UserService;

@Controller
public class BaseController extends selectBean {

	@Autowired
	UserService userService;

	@RequestMapping("/")
	public String index() {
		return "redirect:/top";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	public String loginPost() {
		return "redirect:/login-error";
	}

	@GetMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}

	/** トップページ **/
	@GetMapping("/top")
	public String printUser(Model model) {
		Employee loggedEmployee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Employee employee = userService.findById(loggedEmployee.getId());
		model.addAttribute("employee", employee);
		return "employee";
	}

	/** ログインユーザ 情報変更画面 **/
	@GetMapping("/edit/{id}")
	public String myEdit(Model model, Model stringModel) {
		Employee loggedEmployee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Employee employee = userService.findById(loggedEmployee.getId());
		List<Department> departments = userService.findAllDepartments();
		stringModel.addAttribute("departments", departments);
		stringModel.addAttribute("sexes", getSex);
		model.addAttribute("employeeUpdateRequest", employee);
		return "update_form";
	}

	/**
	 * ログインユーザ 編集処理
	 **/
	@PostMapping("/edit/{id}")
	public String Update(@Validated @ModelAttribute EmployeeUpdateRequest employeeUpdateRequest, BindingResult result,
			Model model, Model stringModel) {

		if (result.hasErrors()) {

			List<Department> departments = userService.findAllDepartments();
			stringModel.addAttribute("departments", departments);
			stringModel.addAttribute("sexes", getSex);
			model.addAttribute("requestDepartment", employeeUpdateRequest.getDepartment());
			model.addAttribute("requestSex", employeeUpdateRequest.getSex());
			model.addAttribute("requestAuthority", employeeUpdateRequest.isAuthority());

			return "update_form";
		}

		userService.myUpdate(employeeUpdateRequest);

		return "redirect:/top";
	}

	/**
	 * パスワード変更画面
	 */
	@GetMapping("/password")
	public String updatePass() {
		return "password_form";
	}

	/**
	 * パスワード変更
	 */
	@PostMapping("/password")
	public String topupdate(@Validated @ModelAttribute PasswordRequest passwordRequest, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError", errorList);
			return "password_form";
		}
		userService.updatePassword(passwordRequest);
		return "redirect:/top";
	}
}
