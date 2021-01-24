package com.example.demo.controller.base;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Employee;
import com.example.demo.service.base.CommonService;

@Controller
public class BaseController extends CommonService {

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
		String email = loggedEmployee.getEmail();
		Employee employee = employeeRepository.findByEmail(email);

		model.addAttribute("employee", employee);
		return "employee";
	}

}
