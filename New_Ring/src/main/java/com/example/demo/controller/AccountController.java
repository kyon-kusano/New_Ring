package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AccountController {


	@Autowired
	private AccountRepository repository;

	@Autowired
	private final DepartmentRepository departmentrepository;

	@Autowired
	private AccountService accountService;

	public List<Employee> get() {
		return (List<Employee>) repository.findAll();
	}

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

	@RequestMapping("/top")
	public String top() {
		return "employee";
	}

	@GetMapping("/index")
	private String Index(Model model) {
	    List<Employee> employees = accountService.findAllList();
	    model.addAttribute("employees", employees);
	    return "index";
	}

	/**
	 * ユーザー新規登録画面を表示
	 * @param model Model
	 * @return ユーザー情報一覧画面
	 */
	@GetMapping("/create")
	public String displayAdd(Model model) {
		model.addAttribute("accountForm", new AccountForm());
		List<Department>departments= accountService.findAllDepartment();
		model.addAttribute("departments", departments);
		return "create_form";
	}



	/**
	 * ユーザー新規登録
	 * @param userRequest リクエストデータ
	 * @param model Model
	 * @return ユーザー情報一覧画面
	 */
	@GetMapping("/create/index")
   public String create(@ModelAttribute AccountForm accountForm, Model model) {
		// ユーザー情報の登録
		accountService.create(accountForm);
		return "redirect:/index";
	}
}
