package com.example.demo.unitTests.controller;

import com.example.demo.dto.Employee;
import com.example.demo.rest.EmployeeController;
import com.example.demo.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {
    @Mock
    EmployeeService employeeService;

    @InjectMocks
    EmployeeController employeeController;

    @Test
    public void getAllEmployee_positive() {
        List<Employee> employeeList = mock(List.class);
        when(employeeService.getAllEmployees()).thenReturn(employeeList);
        ResponseEntity<List<Employee>> responseEntity = employeeController.getAllEmployees();
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), employeeList);
    }

    @Test
    public void getById_positive() {
        Long id = 1L;
        Employee employee = mock(Employee.class);
        when(employeeService.getEmployeeById(eq(id))).thenReturn(Optional.of(employee));
        ResponseEntity<Employee> responseEntity = employeeController.getEmployeeById(id);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), employee);
    }

    @Test
    public void getById_notFound() {
        Long id = 1L;
        when(employeeService.getEmployeeById(eq(id))).thenReturn(Optional.empty());
        ResponseEntity<Employee> responseEntity = employeeController.getEmployeeById(id);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void createEmployee_positive() {
        Employee employee = mock(Employee.class);
        ResponseEntity<Void> responseEntity = employeeController.createEmployee(employee);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void updateEmployee_positive() {
        Long id = 1L;
        Employee employee = mock(Employee.class);
        ResponseEntity<Void> responseEntity = employeeController.updateEmployee(id, employee);
        verify(employeeService, times(1)).updateEmployee(eq(id), eq(employee));
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void deleteEmployee_positive() {
        Long id = 1L;
        ResponseEntity<Void> responseEntity = employeeController.deleteEmployee(id);
        verify(employeeService, times(1)).deleteEmployeeById(eq(id));
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

}
