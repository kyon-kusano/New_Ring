package com.example.demo.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.bean.selectBean;
import com.example.demo.dto.EmployeeRequest;
import com.example.demo.service.admin.AdminService;

@Controller
@RequestMapping("/admin/create")
public class AdminCreateController implements selectBean {

	@Autowired
	AdminService adminService;

	/** 従業員新規登録画面 **/
	@GetMapping
	public String displayAdd(Model model, Model stringModel) {

		stringModel.addAttribute("departments", getDepartments);
		stringModel.addAttribute("sexes", getSex);

		model.addAttribute("employeeRequest", new EmployeeRequest());
		return "admin/create_form";
	}

	/**
	 * 従業員登録処理（一般）
	 */
	@PostMapping(params = "user")
	public String create(@Validated @ModelAttribute EmployeeRequest employeeRequest, BindingResult result, Model model,
			Model stringModel) {

		if (result.hasErrors()) {
			stringModel.addAttribute("departments", getDepartments);
			stringModel.addAttribute("sexes", getSex);
			return "admin/create_form";
		}
		adminService.create(employeeRequest);
		return "redirect:/admin/list";
	}

	/**
	 * 従業員登録処理（管理者）
	 */
	@PostMapping(params = "admin")
	public String createAdmin(@Validated @ModelAttribute EmployeeRequest employeeRequest, BindingResult result,
			Model model, Model stringModel) {

		if (result.hasErrors()) {
			stringModel.addAttribute("departments", getDepartments);
			stringModel.addAttribute("sexes", getSex);
			return "admin/create_form";
		}
		adminService.createAdmin(employeeRequest);
		return "redirect:/admin/list";
	}

}
