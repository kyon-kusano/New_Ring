package com.example.demo.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.bean.selectBean;
import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeUpdateRequest;
import com.example.demo.dto.PasswordRequest;
import com.example.demo.model.entity.Department;
import com.example.demo.model.entity.Employee;
import com.example.demo.model.entity.Pagination;
import com.example.demo.repository.EmployeeRepositoryCustomImpl;
import com.example.demo.service.admin.AdminService;

@Controller
public class AdminController extends selectBean {

	@Autowired
	AdminService adminService;

	@Autowired
	EmployeeRepositoryCustomImpl employeeRepositoryCustomImpl;

	/** 従業員一覧画面 **/
	@GetMapping("/admin/list")
	public String findAllList(Model model, @RequestParam(defaultValue = "1") int page, Model stringModel) {

		List<Department> departments = adminService.findAllDepartments();
		stringModel.addAttribute("departments", departments);
		stringModel.addAttribute("selectColumns", getSelectColumns);

		int totalListCnt = employeeRepositoryCustomImpl.findAllCnt();
		Pagination pagination = new Pagination(totalListCnt, page);
		int startIndex = pagination.getStartIndex();
		int pageSize = pagination.getPageSize();

		List<Employee> employees = employeeRepositoryCustomImpl.findListPaging(startIndex, pageSize);

		model.addAttribute("employees", employees);
		model.addAttribute("pagination", pagination);

		return "admin/list";
	}

	@GetMapping("/admin/list/page={page}&keyword={keyword}")
	public String test1(@PathVariable int page, @PathVariable String keyword, Model model, Model stringModel,
			Model errorModel) {

		if (keyword == "") {
			return "admin/list";
		}

		List<Department> departments = adminService.findAllDepartments();
		stringModel.addAttribute("departments", departments);
		stringModel.addAttribute("selectColumns", getSelectColumns);

		int totalListCnt = employeeRepositoryCustomImpl.findAllCnt(keyword);
		Pagination pagination = new Pagination(totalListCnt, page);
		int startIndex = pagination.getStartIndex();
		int pageSize = pagination.getPageSize();

		List<Employee> employees = employeeRepositoryCustomImpl.searchKeyword(startIndex, pageSize, keyword);

		if (employees.size() == 0) {
			String errorString = "一致するユーザは見つかりませんでした。";
			stringModel.addAttribute("serchedKeyword", keyword);
			errorModel.addAttribute("errorKeyword", errorString);
			model.addAttribute("employees", employees);
			model.addAttribute("pagination", pagination);
		} else {
			stringModel.addAttribute("serchedKeyword", keyword);
			model.addAttribute("employees", employees);
			model.addAttribute("pagination", pagination);
		}

		return "admin/list";
	}

	@GetMapping("/admin/list/page={page}&department={department}&sort={column}")
	public String test3(@PathVariable("page") int page, @PathVariable("department") String department,
			@PathVariable(required = false) String column, Model model, Model stringModel, Model errorModel) {

		List<Department> departments = adminService.findAllDepartments();
		stringModel.addAttribute("departments", departments);
		stringModel.addAttribute("selectColumns", getSelectColumns);
		stringModel.addAttribute("selectedDepartment", department);
		stringModel.addAttribute("selectedColumn", column);

		int totalListCnt = employeeRepositoryCustomImpl.findDepartmentCnt(department, column);
		Pagination pagination = new Pagination(totalListCnt, page);
		int startIndex = pagination.getStartIndex();
		int pageSize = pagination.getPageSize();

		List<Employee> employees = employeeRepositoryCustomImpl.searchDepartment(startIndex, pageSize, department,
				column);

		if (employees.size() == 0) {
			String errorString = "一致するユーザは見つかりませんでした。";

			errorModel.addAttribute("errorDepartment", errorString);
			model.addAttribute("employees", employees);
			model.addAttribute("pagination", pagination);
		} else {

			model.addAttribute("employees", employees);
			model.addAttribute("pagination", pagination);
		}

		return "admin/list";
	}

	@RequestMapping(value = "/admin/list", params = "keyword", method = RequestMethod.POST)
	public String test(@RequestParam(defaultValue = "1") int page, @RequestParam("keyword") String keyword,
			RedirectAttributes redirectAttributes) {

		if (keyword.equals("")) {
			return "redirect:/admin/list";
		} else {
			redirectAttributes.addAttribute("page", page);
			redirectAttributes.addAttribute("keyword", keyword);
			return "redirect:/admin/list/page={page}&keyword={keyword}";
		}
	}

	@RequestMapping(value = "/admin/list", method = RequestMethod.POST)
	public String test1(@RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String department,
			@RequestParam(required = false) String column, RedirectAttributes redirectAttributes) {

		if (department.equals("未選択") && column.equals("default")) {
			return "redirect:/admin/list";
		} else {
			redirectAttributes.addAttribute("page", page);
			redirectAttributes.addAttribute("department", department);
			redirectAttributes.addAttribute("column", column);
			return "redirect:/admin/list/page={page}&department={department}&sort={column}";
		}
	}

	/** 従業員新規登録画面 **/
	@GetMapping("/admin/create")
	public String displayAdd(Model model, Model stringModel) {
		List<Department> departments = adminService.findAllDepartments();
		stringModel.addAttribute("departments", departments);
		stringModel.addAttribute("sexes", getSex);
		model.addAttribute("employeeRequest", new EmployeeRequest());
		return "admin/create_form";
	}

	/**
	 * 従業員登録処理
	 *
	 */
	@PostMapping(value = "/admin/create")
	public String create(@Validated @ModelAttribute EmployeeRequest employeeRequest, BindingResult result,
			Model stringModel, Model model) {

		if (result.hasErrors()) {
			List<Department> departments = adminService.findAllDepartments();
			stringModel.addAttribute("departments", departments);
			stringModel.addAttribute("sexes", getSex);
			model.addAttribute("requestDepartment", employeeRequest.getDepartment());
			model.addAttribute("requestSex", employeeRequest.getSex());
			model.addAttribute("requestAuthority", employeeRequest.isAuthority());
			return "admin/create_form";
		} else if (employeeRequest.getDepartment() == 1) {
			List<Department> departments = adminService.findAllDepartments();
			stringModel.addAttribute("departments", departments);
			stringModel.addAttribute("sexes", getSex);
			model.addAttribute("requestDepartment", employeeRequest.getDepartment());
			model.addAttribute("requestSex", employeeRequest.getSex());
			model.addAttribute("requestAuthority", employeeRequest.isAuthority());
			model.addAttribute("departmentError", "部署を選択してください");
			return "admin/create_form";
		}
		try {
			adminService.create(employeeRequest);
		} catch (DataIntegrityViolationException e) {
			List<Department> departments = adminService.findAllDepartments();
			stringModel.addAttribute("departments", departments);
			stringModel.addAttribute("sexes", getSex);
			model.addAttribute("requestDepartment", employeeRequest.getDepartment());
			model.addAttribute("requestSex", employeeRequest.getSex());
			model.addAttribute("requestAuthority", employeeRequest.isAuthority());
			model.addAttribute("error", "このメールアドレスはすでに登録されています。");
			return "admin/create_form";
		}
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

		List<Department> departments = adminService.findAllDepartments();
		stringModel.addAttribute("departments", departments);
		stringModel.addAttribute("sexes", getSex);

		model.addAttribute("employeeUpdateRequest", employee);
		return "admin/update_form";
	}

	/**
	 * 従業員情報変更処理
	 */
	@PostMapping(value = "/admin/edit/{id}")
	public String update(@Validated @ModelAttribute EmployeeUpdateRequest employeeUpdateRequest, BindingResult result,
			Model stringModel, Model errorModel, Model model, @PathVariable Long id) {

		if (result.hasErrors()) {

			List<Department> departments = adminService.findAllDepartments();
			stringModel.addAttribute("departments", departments);
			stringModel.addAttribute("sexes", getSex);
			model.addAttribute("requestDepartment", employeeUpdateRequest.getDepartment());
			model.addAttribute("requestSex", employeeUpdateRequest.getSex());
			model.addAttribute("requestAuthority", employeeUpdateRequest.isAuthority());

			return "admin/update_form";
		}
		if (employeeUpdateRequest.getDepartment() == 1) {

			List<Department> departments = adminService.findAllDepartments();
			stringModel.addAttribute("departments", departments);
			stringModel.addAttribute("sexes", getSex);
			model.addAttribute("requestDepartment", employeeUpdateRequest.getDepartment());
			model.addAttribute("requestSex", employeeUpdateRequest.getSex());
			model.addAttribute("requestAuthority", employeeUpdateRequest.isAuthority());
			model.addAttribute("departmentError", "部署を選択してください");
			return "admin/update_form";
		}

		try {
			adminService.update(employeeUpdateRequest);
		} catch (DataIntegrityViolationException e) {
			List<Department> departments = adminService.findAllDepartments();
			stringModel.addAttribute("departments", departments);
			stringModel.addAttribute("sexes", getSex);
			model.addAttribute("requestDepartment", employeeUpdateRequest.getDepartment());
			model.addAttribute("requestSex", employeeUpdateRequest.getSex());
			model.addAttribute("requestAuthority", employeeUpdateRequest.isAuthority());
			model.addAttribute("error", "このメールアドレスはすでに登録されています。");
			return "admin/update_form";
		}

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
