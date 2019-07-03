package com.greenfox.javatribes.javatribes;

import com.greenfox.javatribes.javatribes.exceptions.EntityNotFoundException;
import com.greenfox.javatribes.javatribes.model.TestUtil;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.restcontrollers.LoginRestController;
import com.greenfox.javatribes.javatribes.security.JWTTokenUtil;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginRestController.class)

public class LoginRestControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private JWTTokenUtil jwtTokenUtil;
    @MockBean
    private User authUser;
    @MockBean
    private EntityNotFoundException exception;

    @Test
    public void successfulLoginUserTest() throws Exception {

        User user = new User("Juraj", "GreenFox");

        when(userService.findByUsernameAndPassword("Juraj", "GreenFox")).thenReturn(user);

        RequestBuilder request = post("/login")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user));

        ResultActions resultActions = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("{\"status\":\"ok\"}"))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        //MvcResult result = resultActions.andReturn();

    }

    @Test
    public void unsuccessfulLoginUserTestWithEntityNotFoundException() throws Exception {

        User user = new User("Juraj", "GreenFox");

        when(userService.findByUsernameAndPassword("Juraj", "GreenFox")).thenThrow(new EntityNotFoundException("No such user - wrong username or password."));

        RequestBuilder request = post("/login")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user));

        ResultActions resultActions = mockMvc.perform(request)
                .andExpect(status().is(401))
                //.andExpect(status().reason(containsString("No such user - wrong username or password.")))
                .andExpect(content().string("{\"status\":\"error\",\"message\":\"No such user - wrong username or password.\"}"))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        //MvcResult result = resultActions.andReturn();

    }
}
