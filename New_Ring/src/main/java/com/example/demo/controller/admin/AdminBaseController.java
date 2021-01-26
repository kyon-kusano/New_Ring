package com.example.demo.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.demo.bean.selectBean;
import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeUpdateRequest;
import com.example.demo.dto.PasswordRequest;
import com.example.demo.model.entity.Employee;
import com.example.demo.service.admin.AdminService;

@Controller
public class AdminBaseController extends selectBean {

	@Autowired
	AdminService adminService;

	/** 従業員一覧画面 **/
	@RequestMapping("/admin/list")
	public String getWordList(Model model, Pageable pageable) {
		model.addAttribute("page", adminService.lisultList);
		model.addAttribute("employees", adminService.lisultList.getContent());
		return "admin/list";
	}

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
	@PostMapping(value = "/admin/create", params = "user")
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
	@PostMapping(value = "/admin/create", params = "admin")
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

	/** 従業員詳細画面 **/
	@GetMapping("/admin/{id}")
	public String displayView(@PathVariable Long id, Model model) {
		Employee employee = adminService.findById(id);
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

	/** 従業員情報変更画面 **/
	@GetMapping("/admin/edit/{id}")
	public String displayEdit(@PathVariable Long id, Model model, Model stringModel) {
		Employee employee = adminService.findById(id);

		stringModel.addAttribute("departments", getDepartments);
		stringModel.addAttribute("sexes", getSex);

		model.addAttribute("employeeUpdateRequest", employee);
		return "admin/update_form";
	}

	/**
	 * 従業員情報変更処理
	 */
	@PostMapping(value = "/admin/edit/{id}", params = "update")
	public String update(@Validated @ModelAttribute EmployeeUpdateRequest employeeUpdateRequest, BindingResult result,
			Model model, Model stringModel) {

		if (result.hasErrors()) {
			stringModel.addAttribute("departments", getDepartments);
			stringModel.addAttribute("sexes", getSex);
			return "admin/update_form";
		}

		adminService.update(employeeUpdateRequest);
		return String.format("redirect:/admin/%d", employeeUpdateRequest.getId());
	}

	/**
	 * 管理者権限追加処理
	 */
	@PostMapping(value = "/admin/edit/{id}", params = "addAdmin")
	public String addAdmin(@Validated @ModelAttribute EmployeeUpdateRequest employeeUpdateRequest, BindingResult result,
			Model model, Model stringModel) {

		adminService.addAdmin(employeeUpdateRequest);
		return String.format("redirect:/admin/%d", employeeUpdateRequest.getId());
	}

	/**
	 * 管理者権限削除処理
	 */
	@PostMapping(value = "/admin/edit/{id}", params = "removeAdmin")
	public String removeAdmin(@Validated @ModelAttribute EmployeeUpdateRequest employeeUpdateRequest,
			BindingResult result, Model model, Model stringModel) {
		adminService.removeAdmin(employeeUpdateRequest);
		return String.format("redirect:/admin/%d", employeeUpdateRequest.getId());
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
