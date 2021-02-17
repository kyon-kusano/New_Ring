package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
