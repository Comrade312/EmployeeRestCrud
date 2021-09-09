package com.example.demo;

import com.example.demo.dto.Employee;
import com.example.demo.exceptions.employee.EmployeeNotFoundException;
import com.example.demo.exceptions.request.BadRequestParametersException;
import com.example.demo.rest.EmployeeController;
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
@Sql(value = {"/department_list_before.sql", "/employee_list_before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/department_list_after.sql", "/employee_list_after.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithMockUser("Bob")
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeController employeeController;

    @Test
    public void getAllEmployeeTest() throws Exception {
        this.mockMvc.perform(get("/api/employee/all"))
                .andExpect(authenticated())
                .andExpect(jsonPath("$.length()", is(5)));
    }

    @Test
    public void getEmployeeById() throws Exception {
        this.mockMvc.perform(get("/api/employee/1"))
                .andExpect(authenticated())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void getEmployeeByNonExistId() throws Exception {
        this.mockMvc.perform(get("/api/employee/-1"))
                .andExpect(authenticated())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteEmployeeById() throws Exception {
        this.mockMvc.perform(delete("/api/employee/1").with(csrf()))
                .andExpect(authenticated())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteEmployeeNonExistById() throws Exception {
        this.mockMvc.perform(delete("/api/employee/-1").with(csrf()))
                .andExpect(authenticated())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmployeeNotFoundException));
    }

    @Test
    public void updateEmployeeById() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("TestName");

        Gson gson = new Gson();
        String json = gson.toJson(employee);

        MockHttpServletRequestBuilder requestBuilder = put("/api/employee/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(csrf());

        this.mockMvc.perform(requestBuilder)
                .andExpect(authenticated())
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/api/employee/1"))
                .andExpect(authenticated())
                .andExpect(jsonPath("$.firstName", is("TestName")));

    }

    @Test
    public void updateEmployeeByWithConflictId() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("TestName");

        Gson gson = new Gson();
        String json = gson.toJson(employee);

        MockHttpServletRequestBuilder requestBuilder = put("/api/employee/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(csrf());

        this.mockMvc.perform(requestBuilder)
                .andExpect(authenticated())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestParametersException));

    }

    @Test
    public void updateEmployeeByNonExistId() throws Exception {
        Employee employee = new Employee();
        employee.setId(-1L);
        employee.setFirstName("TestName");

        Gson gson = new Gson();
        String json = gson.toJson(employee);

        MockHttpServletRequestBuilder requestBuilder = put("/api/employee/-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(csrf());

        this.mockMvc.perform(requestBuilder)
                .andExpect(authenticated())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmployeeNotFoundException));

    }

    @Test
    public void createEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("TestName");
        employee.setLastName("TestSurname");

        Gson gson = new Gson();
        String json = gson.toJson(employee);

        MockHttpServletRequestBuilder requestBuilder = post("/api/employee/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(csrf());

        this.mockMvc.perform(requestBuilder)
                .andExpect(authenticated())
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/api/employee/6"))
                .andExpect(authenticated())
                .andExpect(jsonPath("$.firstName", is("TestName")))
                .andExpect(jsonPath("$.lastName", is("TestSurname")));

    }


}
