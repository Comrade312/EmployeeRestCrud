package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void unAuthorizeTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "Bob")
    public void authorizeTest() throws Exception {
        this.mockMvc.perform(get("/api/employee/all"))
                .andExpect(status().isOk());
    }

}
