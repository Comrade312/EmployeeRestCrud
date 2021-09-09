package com.example.demo.unitTests.service;

import com.example.demo.dao.EmployeeDao;
import com.example.demo.dto.Department;
import com.example.demo.dto.Employee;
import com.example.demo.exceptions.employee.EmployeeNotFoundException;
import com.example.demo.exceptions.request.BadRequestParametersException;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @InjectMocks
    EmployeeService employeeService;
    @Mock
    EmployeeDao employeeDao;
    @Mock
    DepartmentService departmentService;
    @Mock
    Employee employeeMock;
    @Mock
    Department departmentMock;

    @Test
    public void createEmployee_positive() {
        employeeService.createEmployee(employeeMock);
        verify(employeeDao, times(1)).save(eq(employeeMock));
    }

    @Test
    public void getAllEmployee_positive() {
        List<Employee> mockedList = mock(List.class);
        when(employeeDao.findAll()).thenReturn(mockedList);
        assertEquals(employeeService.getAllEmployees(), mockedList);
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void deleteEmployee_throwEmployeeNotFound() {
        Long id = 1L;
        when(employeeDao.findById(eq(id))).thenReturn(Optional.empty());
        employeeService.deleteEmployeeById(id);
    }

    @Test
    public void deleteEmployee_positive() {
        Long id = 1L;
        Optional<Employee> employee = Optional.of(employeeMock);
        when(employeeDao.findById(eq(id))).thenReturn(employee);
        employeeService.deleteEmployeeById(id);
        verify(employeeDao, times(1)).deleteById(eq(id));
    }

    @Test
    public void getById_positive() {
        Long id = 1L;
        when(employeeDao.findById(eq(id))).thenReturn(Optional.of(employeeMock));
        assertEquals(Optional.of(employeeMock), employeeService.getEmployeeById(id));
    }

    @Test
    public void updateEmployee_positive() {
        Long id = 1L;
        Long depId = 1L;

        Optional<Employee> employee = Optional.of(employeeMock);

        when(employeeMock.getId()).thenReturn(id);
        when(employeeDao.findById(eq(id))).thenReturn(employee);

        when(employeeMock.getDepartment()).thenReturn(departmentMock);
        when(departmentMock.getId()).thenReturn(depId);
        when(departmentService.getDepartmentById(eq(depId))).thenReturn(Optional.of(departmentMock));

        employeeService.updateEmployee(id, employeeMock);
        verify(employeeDao, times(1)).save(eq(employeeMock));
    }

    @Test(expected = BadRequestParametersException.class)
    public void updateEmployee_throwBadRequestParameters() {
        Long id = 1L;
        when(employeeMock.getId()).thenReturn(2L);
        employeeService.updateEmployee(id, employeeMock);
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void updateEmployee_throwEmployeeNotFound() {
        Long id = 1L;
        when(employeeMock.getId()).thenReturn(id);
        when(employeeDao.findById(employeeMock.getId())).thenReturn(Optional.empty());
        employeeService.updateEmployee(id, employeeMock);
    }

}