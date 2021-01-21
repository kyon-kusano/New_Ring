package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {
	
	@GetMapping("login")
	private String Login() {
		return "login";
	}
	
	@GetMapping("index")
	private String Index() {
		return "index";
	}
	
	@GetMapping("detail")
	private String Detail() {
		return "detail";
	}
	
	@GetMapping("update")
	private String Edit() {
		return "update_form";
	}
	
	@GetMapping("create")
	private String Create() {
		return "create_form";
	}
}
