package com.example.demo.exceptions.department;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException() {
        super("Department not found");
    }

    public DepartmentNotFoundException(String name) {
        super("Can't find department with name = " + name);
    }

    public DepartmentNotFoundException(Long id) {
        super("Can't find department with id = " + id);
    }

}