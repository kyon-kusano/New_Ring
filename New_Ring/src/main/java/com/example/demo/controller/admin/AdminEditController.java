package com.example.demo.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.bean.selectBean;
import com.example.demo.dto.EmployeeUpdateRequest;
import com.example.demo.entity.Employee;
import com.example.demo.service.admin.AdminService;
import com.example.demo.service.base.BaseService;

@Controller
@RequestMapping("/admin/edit/{id}")
public class AdminEditController implements selectBean {

	@Autowired
	BaseService baseService;
	@Autowired
	AdminService adminService;

	/** 従業員情報変更画面 **/
	@GetMapping
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
	@RequestMapping(params = "update")
	public String update(@Validated @ModelAttribute EmployeeUpdateRequest employeeUpdateRequest, BindingResult result,
			Model model, Model stringModel) {

		if (result.hasErrors()) {
			stringModel.addAttribute("departments", getDepartments);
			stringModel.addAttribute("sexes", getSex);
			return "admin/update_form";
		}

		baseService.update(employeeUpdateRequest);
		return String.format("redirect:/admin/%d", employeeUpdateRequest.getId());
	}

	/**
	 * 管理者権限追加処理
	 */
	@RequestMapping(params = "addAdmin")
	public String addAdmin(@Validated @ModelAttribute EmployeeUpdateRequest employeeUpdateRequest, BindingResult result,
			Model model, Model stringModel) {

		adminService.addAdmin(employeeUpdateRequest);
		return String.format("redirect:/admin/%d", employeeUpdateRequest.getId());
	}

	/**
	 * 管理者権限削除処理
	 */
	@RequestMapping(params = "removeAdmin")
	public String removeAdmin(@Validated @ModelAttribute EmployeeUpdateRequest employeeUpdateRequest,
			BindingResult result, Model model, Model stringModel) {

		adminService.removeAdmin(employeeUpdateRequest);
		return String.format("redirect:/admin/%d", employeeUpdateRequest.getId());
	}

}
