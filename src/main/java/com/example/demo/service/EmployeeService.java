package com.example.demo.service;

import com.example.demo.dao.EmployeeDao;
import com.example.demo.dto.Department;
import com.example.demo.dto.Employee;
import com.example.demo.exceptions.employee.EmployeeNotFoundException;
import com.example.demo.exceptions.request.BadRequestParametersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DepartmentService departmentService;

    public void createEmployee(Employee employee) {
        employeeDao.save(employee);
    }

    public void updateEmployee(Long id, Employee employee) {
        if (employee != null && id.equals(employee.getId())) {
            if (!employeeDao.findById(employee.getId()).isPresent()) {
                throw new EmployeeNotFoundException(employee.getId());
            }

            if (employee.getDepartment() != null) {
                employee.setDepartment(
                        departmentService.getDepartmentById(employee.getDepartment().getId()).orElse(null)
                );
            }

            employeeDao.save(employee);
        } else {
            throw new BadRequestParametersException("Error in data:" +
                    " path variable id must be not null and equal to employee id");
        }

    }

    public void deleteEmployeeById(Long id) {
        if (!employeeDao.findById(id).isPresent()) {
            throw new EmployeeNotFoundException(id);
        }
        employeeDao.deleteById(id);
    }

    @Cacheable(cacheNames = "employeeCache")
    public List<Employee> getAllEmployees() {
        return employeeDao.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeDao.findById(id);
    }
}
