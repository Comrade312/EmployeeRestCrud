package com.example.demo.unitTests.controller;

import com.example.demo.dto.Department;
import com.example.demo.rest.DepartmentController;
import com.example.demo.service.DepartmentService;
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
public class DepartmentControllerTest {
    @Mock
    DepartmentService departmentService;

    @InjectMocks
    DepartmentController departmentController;

    @Test
    public void getAllDepartment_positive() {
        List<Department> departmentList = mock(List.class);
        when(departmentService.getAllDepartments()).thenReturn(departmentList);
        ResponseEntity<List<Department>> responseEntity = departmentController.getAllDepartments();
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), departmentList);
    }

    @Test
    public void getById_positive() {
        Long id = 1L;
        Department department = mock(Department.class);
        when(departmentService.getDepartmentById(eq(id))).thenReturn(Optional.of(department));
        ResponseEntity<Department> responseEntity = departmentController.getDepartmentById(id);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), department);
    }

    @Test
    public void getById_notFound() {
        Long id = 1L;
        when(departmentService.getDepartmentById(eq(id))).thenReturn(Optional.empty());
        ResponseEntity<Department> responseEntity = departmentController.getDepartmentById(id);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void createDepartment_positive() {
        Department Department = mock(Department.class);
        ResponseEntity<Void> responseEntity = departmentController.createDepartment(Department);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void updateDepartment_positive() {
        Long id = 1L;
        Department Department = mock(Department.class);
        ResponseEntity<Void> responseEntity = departmentController.updateDepartment(id, Department);
        verify(departmentService, times(1)).updateDepartment(eq(id), eq(Department));
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void deleteDepartment_positive() {
        Long id = 1L;
        ResponseEntity<Void> responseEntity = departmentController.deleteDepartment(id);
        verify(departmentService, times(1)).deleteDepartmentById(eq(id));
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

}
