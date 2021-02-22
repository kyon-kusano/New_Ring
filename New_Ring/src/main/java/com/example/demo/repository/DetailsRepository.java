package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.entity.Details;

public interface DetailsRepository extends JpaRepository<Details, Long> {

}
