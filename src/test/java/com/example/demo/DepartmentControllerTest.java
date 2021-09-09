package com.example.demo;

import com.example.demo.dto.Department;
import com.example.demo.exceptions.department.DepartmentNotFoundException;
import com.example.demo.exceptions.request.BadRequestParametersException;
import com.example.demo.rest.DepartmentController;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/department_list_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/department_list_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithMockUser("Bob")
public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartmentController departmentController;

    @Test
    public void getAllDepartmentTest() throws Exception {
        this.mockMvc.perform(get("/api/department/all"))
                .andExpect(authenticated())
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    public void getDepartmentById() throws Exception {
        this.mockMvc.perform(get("/api/department/1"))
                .andExpect(authenticated())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void getDepartmentByNonExistId() throws Exception {
        this.mockMvc.perform(get("/api/department/-1"))
                .andExpect(authenticated())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteDepartmentById() throws Exception {
        this.mockMvc.perform(delete("/api/department/1").with(csrf()))
                .andExpect(authenticated())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteDepartmentNonExistById() throws Exception {
        this.mockMvc.perform(delete("/api/department/-1").with(csrf()))
                .andExpect(authenticated())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DepartmentNotFoundException));
    }

    @Test
    public void updateDepartmentById() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("TestName");

        Gson gson = new Gson();
        String json = gson.toJson(department);

        MockHttpServletRequestBuilder requestBuilder = put("/api/department/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(csrf());

        this.mockMvc.perform(requestBuilder)
                .andExpect(authenticated())
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/api/department/1"))
                .andExpect(authenticated())
                .andExpect(jsonPath("$.name", is("TestName")));

    }

    @Test
    public void updateDepartmentByWithConflictId() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("TestName");

        Gson gson = new Gson();
        String json = gson.toJson(department);

        MockHttpServletRequestBuilder requestBuilder = put("/api/department/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(csrf());

        this.mockMvc.perform(requestBuilder)
                .andExpect(authenticated())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestParametersException));

    }

    @Test
    public void updateDepartmentByNonExistId() throws Exception {
        Department department = new Department();
        department.setId(-1L);
        department.setName("TestName");

        Gson gson = new Gson();
        String json = gson.toJson(department);

        MockHttpServletRequestBuilder requestBuilder = put("/api/department/-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(csrf());

        this.mockMvc.perform(requestBuilder)
                .andExpect(authenticated())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DepartmentNotFoundException));

    }

    @Test
    public void createDepartment() throws Exception {
        Department department = new Department();
        department.setName("TestName");

        Gson gson = new Gson();
        String json = gson.toJson(department);

        MockHttpServletRequestBuilder requestBuilder = post("/api/department/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(csrf());

        this.mockMvc.perform(requestBuilder)
                .andExpect(authenticated())
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/api/department/4"))
                .andExpect(authenticated())
                .andExpect(jsonPath("$.name", is("TestName")));

    }

}
