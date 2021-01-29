package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccountController {


	@Autowired
	private AccountRepository repository;

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
}
