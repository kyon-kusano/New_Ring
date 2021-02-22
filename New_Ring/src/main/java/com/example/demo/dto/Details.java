package com.example.demo.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Details implements Serializable {

	private MultipartFile multipartFile;

	private String comment;

}