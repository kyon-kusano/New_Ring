package com.example.demo.repository;

import com.example.demo.model.entity.ProgrammingLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProgrammingLanguageRepository
        extends JpaRepository<ProgrammingLanguage, String>, JpaSpecificationExecutor<ProgrammingLanguage> {
    ProgrammingLanguage findByName(String name);

    @Query(nativeQuery = true, value = "select g.name from programming_language g")
    List<ProgrammingLanguage> findNameAllList();
}
