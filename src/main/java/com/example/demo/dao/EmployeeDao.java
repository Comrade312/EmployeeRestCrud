package com.example.demo.dao;

import com.example.demo.dto.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeDao extends CrudRepository<Employee, Long> {
    List<Employee> findAll();
}
