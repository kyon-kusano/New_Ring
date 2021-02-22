package com.example.demo.controller.base;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.bean.selectBean;
import com.example.demo.dto.Details;
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
		model.addAttribute("details", new Details());
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

	@GetMapping("/edit/details/{id}")
	public String myDetailsEdit(@PathVariable Long id, Model model) {
		Employee employee = userService.findById(id);
		model.addAttribute("details", new Details());
		model.addAttribute("employee", employee);
		return "details_update_form";
	}

	@PostMapping("/edit/details/{id}")
	public Object upload(Details details, @PathVariable Long id, RedirectAttributes redirectAttributes) {
		if (!details.getMultipartFile().isEmpty()) {
			String extension = "";
			String filePath = "images/employee/";

			Employee employee = userService.findById(id);
			String username = employee.getUsername();
			String comment = details.getComment();

			File uploadDir = mkdirs(filePath, username);
			uploadDir = mkdirs(uploadDir.toString(), "icon");

			File requestFile = new File(details.getMultipartFile().getOriginalFilename());
			String requestFileName = requestFile.getName();
			extension = requestFileName.substring(requestFileName.lastIndexOf("."));
			String renameFileName = username + extension;

			if (employee.getDetails() != null) {
				String emplyeeImage = employee.getDetails().getImage();
				File file = new File(emplyeeImage);
				file.delete();
			}

			try {
				byte[] bytes = details.getMultipartFile().getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(uploadDir.toString() + "/" + renameFileName)));
				stream.write(bytes);
				stream.close();
				userService.updateDetails(id, uploadDir.toString() + "/" + renameFileName, comment);
				redirectAttributes.addAttribute("id", id);
			} catch (Exception e) {
				return "redirect:/edit/details/{id}";
			} catch (Throwable t) {
				return "redirect:/edit/details/{id}";
			}
			return "redirect:/top";
		} else {
			return "redirect:/edit/details/{id}";
		}

	}

	private File mkdirs(String filePath, String profile) {
		File uploadDir = new File(filePath, profile);
		uploadDir.mkdirs();
		return uploadDir;
	}
}
