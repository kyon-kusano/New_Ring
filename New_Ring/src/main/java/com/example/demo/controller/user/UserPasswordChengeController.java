package com.example.demo.controller.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.PasswordRequest;
import com.example.demo.service.base.BaseService;
import com.example.demo.service.user.UserService;

@Controller
@RequestMapping("/password")
public class UserPasswordChengeController {

	@Autowired
	BaseService baseService;
	@Autowired
	UserService userService;

	/**
	 * パスワード変更画面
	 */
	@GetMapping
	public String updatePass() {
		return "password_form";
	}

	/**
	 * パスワード変更
	 */
	@PostMapping
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
