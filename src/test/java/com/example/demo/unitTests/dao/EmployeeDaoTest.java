package com.example.demo.unitTests.dao;

import com.example.demo.dao.EmployeeDao;
import com.example.demo.dto.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeDaoTest {
    @Mock
    Employee employeeMock;

    @Mock
    List<Employee> employeeListMock;

    @Mock
    EmployeeDao employeeDao;

    @Test
    public void savingEmployee_positive() {
        employeeDao.save(employeeMock);
        verify(employeeDao, times(1)).save(eq(employeeMock));
    }

    @Test
    public void getEmployeeById_positive() {
        Long id = 1L;
        when(employeeDao.findById(eq(id))).thenReturn(Optional.of(employeeMock));
        assertEquals(employeeDao.findById(id), Optional.of(employeeMock));
    }

    @Test
    public void getByNonexistentId_notPresented() {
        Long id = 1L;
        when(employeeDao.findById(eq(id))).thenReturn(Optional.empty());
        assertFalse(employeeDao.findById(id).isPresent());
    }

    @Test
    public void getAllEmployees_positive() {
        when(employeeDao.findAll()).thenReturn(employeeListMock);
        assertEquals(employeeListMock, employeeDao.findAll());
    }

    @Test
    public void employeeDelete_positive() {
        Long id = 1L;
        employeeDao.deleteById(id);
        verify(employeeDao, times(1)).deleteById(eq(id));
    }

}