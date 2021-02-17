package com.example.demo.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmployeeUpdateRequest extends EmployeeRequest implements Serializable {

	private Long id;

}
