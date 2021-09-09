package com.example.demo.unitTests.dao;

import com.example.demo.dao.DepartmentDao;
import com.example.demo.dto.Department;
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
public class DepartmentDaoTest {
    @Mock
    Department departmentMock;

    @Mock
    List<Department> departmentListMock;

    @Mock
    DepartmentDao departmentDao;

    @Test
    public void savingDepartment_positive() {
        departmentDao.save(departmentMock);
        verify(departmentDao, times(1)).save(eq(departmentMock));
    }

    @Test
    public void getDepartmentById_positive() {
        Long id = 1L;
        when(departmentDao.findById(eq(id))).thenReturn(Optional.of(departmentMock));
        assertEquals(departmentDao.findById(id), Optional.of(departmentMock));
    }

    @Test
    public void getByNonexistentId_notPresented() {
        Long id = 1L;
        when(departmentDao.findById(eq(id))).thenReturn(Optional.empty());
        assertFalse(departmentDao.findById(id).isPresent());
    }

    @Test
    public void getAllDepartments_positive() {
        when(departmentDao.findAll()).thenReturn(departmentListMock);
        assertEquals(departmentListMock, departmentDao.findAll());
    }

    @Test
    public void departmentDelete_positive() {
        Long id = 1L;
        departmentDao.deleteById(id);
        verify(departmentDao, times(1)).deleteById(eq(id));
    }

}