package com.example.demo.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.PasswordRequest;
import com.example.demo.entity.Employee;
import com.example.demo.service.admin.AdminService;
import com.example.demo.service.base.BaseService;
import com.example.demo.service.base.CommonService;

@Controller
public class AdminBaseController extends CommonService {

	@Autowired
	BaseService baseService;
	@Autowired
	AdminService adminService;

	/** 従業員一覧画面 **/
	@RequestMapping("/admin/list")
	public String getWordList(Model model, Pageable pageable) {

		Page<Employee> employees = baseService.getAllWord(pageable);
		model.addAttribute("page", employees);
		model.addAttribute("employees", employees.getContent());

		return "admin/list";
	}

	/** 従業員詳細画面 **/
	@GetMapping("/admin/{id}")
	public String displayView(@PathVariable Long id, Model model) {
		Employee employee = baseService.findById(id);
		model.addAttribute("employee", employee);
		return "admin/employee";
	}

	/**
	 * 従業員削除処理
	 **/
	@GetMapping("/admin/delete/{id}")
	public String delete(@PathVariable Long id) {
		adminService.delete(id);
		return "redirect:/admin/list";
	}

	/**
	 * パスワード変更画面
	 */
	@GetMapping("/admin/password/{id}")
	public String updatePass(@PathVariable Long id) {
		return "admin/password_form";
	}

	/**
	 * パスワード変更
	 */
	@PostMapping("/admin/password/{id}")
	public String topupdate(@PathVariable Long id, @Validated @ModelAttribute PasswordRequest passwordRequest,
			BindingResult result, Model model) {

		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError", errorList);
			return "/admin/password_form";
		}
		adminService.adminUpdatePassword(id, passwordRequest);
		return "redirect:/admin/{id}";
	}

}
