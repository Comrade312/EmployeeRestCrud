package com.example.demo.dao;

import com.example.demo.dto.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepartmentDao extends CrudRepository<Department, Long> {
    List<Department> findAll();
}
