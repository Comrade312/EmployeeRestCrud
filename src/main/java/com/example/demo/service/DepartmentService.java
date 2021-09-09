package com.example.demo.service;

import com.example.demo.dao.DepartmentDao;
import com.example.demo.dto.Department;
import com.example.demo.exceptions.department.DepartmentNotFoundException;
import com.example.demo.exceptions.request.BadRequestParametersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentDao departmentDao;

    public void createDepartment(Department department) {
        departmentDao.save(department);
    }

    public void updateDepartment(Long id, Department department) {
        if (department != null && id.equals(department.getId())) {
            if (!departmentDao.findById(department.getId()).isPresent()) {
                throw new DepartmentNotFoundException(department.getId());
            }
            departmentDao.save(department);
        } else {
            throw new BadRequestParametersException("Error in data:" +
                    " path variable id must be not null and equal to department id");
        }
    }

    public void deleteDepartmentById(Long id) {
        if (!departmentDao.findById(id).isPresent()) {
            throw new DepartmentNotFoundException(id);
        }
        departmentDao.deleteById(id);
    }

    public List<Department> getAllDepartments() {
        return departmentDao.findAll();
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentDao.findById(id);
    }
}
