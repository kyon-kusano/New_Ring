package com.example.demo.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Details implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String image;

	private String comment;

	protected Details() {

	}

	public Details(String image) {
		this.image = image;

	}

	public Details(String image, String comment) {
		this.image = image;
		this.comment = comment;

	}

}