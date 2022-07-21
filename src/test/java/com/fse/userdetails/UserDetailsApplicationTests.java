package com.fse.userdetails;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.userdetails.model.request.UserRequest;
import com.fse.userdetails.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("junit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDetailsApplicationTests {

    @Autowired
    private WebApplicationContext webAppContext;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper ob;
    @Autowired
    private UserService userService;

    @BeforeEach
    public void initialize() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webAppContext)
                .build();
    }

    @Test
    @Order(1)
    public void testA1ForConnect() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/getPing")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(equalTo("Success")));

    }

    @Test
    @Order(2)
    public void testA2CreateUser() throws Exception {
        UserRequest user = new UserRequest();
        user.setEmail("abc@gmail.com");
        user.setFirstName("abc");
        user.setLastName("def");
        user.setPassword("abc@123");
        String requestJson = ob.writeValueAsString(user);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/createUser")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    @Order(3)
    public void testA3CreateUserAlreadyExistError() throws Exception {
        UserRequest user = new UserRequest();
        user.setEmail("abc@gmail.com");
        user.setFirstName("abc");
        user.setLastName("def");
        user.setPassword("abc@123");
        String requestJson = ob.writeValueAsString(user);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/createUser")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isConflict());

    }

    @Test
    @Order(4)
    public void testA4ManualLogin() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("userName","abc@gmail.com");
        map.put("password","abc@123");
        String requestJson = ob.writeValueAsString(map);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @Order(5)
    public void testA5ManualLoginWrongPasswordError() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("userName","abc@gmail.com");
        map.put("password","212");
        String requestJson = ob.writeValueAsString(map);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    @Order(6)
    public void testA6ManualLoginUseNotFound() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("userName","google@gmail.com");
        map.put("password","212");
        String requestJson = ob.writeValueAsString(map);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    @Order(7)
    public void testA7ValidateToken() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("userName","abc@gmail.com");
        map.put("password","abc@123");
        map = userService.manualLogin(map);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/validateToken")
                .accept(MediaType.APPLICATION_JSON)
                .header("auth-token", map.get("auth-token")))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}
