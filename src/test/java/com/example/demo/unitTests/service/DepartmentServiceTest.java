package com.example.demo.unitTests.service;

import com.example.demo.dao.DepartmentDao;
import com.example.demo.dto.Department;
import com.example.demo.exceptions.department.DepartmentNotFoundException;
import com.example.demo.exceptions.request.BadRequestParametersException;
import com.example.demo.service.DepartmentService;
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
public class DepartmentServiceTest {

    @InjectMocks
    DepartmentService departmentService;
    @Mock
    DepartmentDao departmentDao;
    @Mock
    Department departmentMock;

    @Test
    public void createDepartment_positive() {
        departmentService.createDepartment(departmentMock);
        verify(departmentDao, times(1)).save(eq(departmentMock));
    }

    @Test
    public void getAllDepartment_positive() {
        List<Department> mockedList = mock(List.class);
        when(departmentDao.findAll()).thenReturn(mockedList);
        assertEquals(departmentService.getAllDepartments(), mockedList);
    }

    @Test(expected = DepartmentNotFoundException.class)
    public void deleteDepartment_throwDepartmentNotFound() {
        Long id = 1L;
        when(departmentDao.findById(eq(id))).thenReturn(Optional.empty());
        departmentService.deleteDepartmentById(id);
    }

    @Test
    public void deleteDepartment_positive() {
        Long id = 1L;
        Optional<Department> department = Optional.of(departmentMock);
        when(departmentDao.findById(eq(id))).thenReturn(department);
        departmentService.deleteDepartmentById(id);
        verify(departmentDao, times(1)).deleteById(eq(id));
    }

    @Test
    public void getById_positive() {
        Long id = 1L;
        when(departmentDao.findById(eq(id))).thenReturn(Optional.of(departmentMock));
        assertEquals(Optional.of(departmentMock), departmentService.getDepartmentById(id));
    }

    @Test
    public void updateDepartment_positive() {
        Long id = 1L;

        Optional<Department> department = Optional.of(departmentMock);

        when(departmentMock.getId()).thenReturn(id);
        when(departmentDao.findById(eq(id))).thenReturn(department);

        departmentService.updateDepartment(id, departmentMock);
        verify(departmentDao, times(1)).save(eq(departmentMock));
    }

    @Test(expected = BadRequestParametersException.class)
    public void updateDepartment_throwBadRequestParameters() {
        Long id = 1L;
        when(departmentMock.getId()).thenReturn(2L);
        departmentService.updateDepartment(id, departmentMock);
    }

    @Test(expected = DepartmentNotFoundException.class)
    public void updateDepartment_throwDepartmentNotFound() {
        Long id = 1L;
        when(departmentMock.getId()).thenReturn(id);
        when(departmentDao.findById(departmentMock.getId())).thenReturn(Optional.empty());
        departmentService.updateDepartment(id, departmentMock);
    }

}