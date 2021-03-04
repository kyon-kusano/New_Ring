package com.example.demo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class DetailsRequest implements Serializable {

    private MultipartFile multipartFile;

    private String comment;

    private String[] programming_Language_Names;
}
