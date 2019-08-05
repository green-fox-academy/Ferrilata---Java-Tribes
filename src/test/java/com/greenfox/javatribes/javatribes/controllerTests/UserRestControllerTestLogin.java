package com.greenfox.javatribes.javatribes.controllerTests;

import com.greenfox.javatribes.javatribes.dto.ResponseObject;
import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.TestUtil;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.restcontrollers.UserRestController;
import com.greenfox.javatribes.javatribes.security.JwtTokenProvider;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserRestController.class)

public class UserRestControllerTestLogin {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    AuthenticationManager manager;

    private String username = "username";
    private String password = "password";
    private String jwt = "jwtSecurityToken";
    private String reqContent = ("{\"username\":\"" + this.username + "\",\"password\":\"" + this.password + "\"}");
    private String errMessage = "No such user - wrong username or password.";

    @Test
    @WithMockUser
    public void successfulLoginUserTest() throws Exception {

        when(this.userService.login(this.username, this.password)).thenReturn(this.jwt);

        RequestBuilder request = post("/login")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(this.reqContent);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("{\"status\":\"ok\",\"token\":\"jwtSecurityToken\"}"))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
    }

    @Test
    public void unsuccessfulLoginUserTestThrowsEntityNotFoundException() throws Exception {

        when(this.userService.login(this.username, this.password))
                .thenThrow(new CustomException(this.errMessage, HttpStatus.valueOf(401)));

        RequestBuilder request = post("/login")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(this.reqContent);

        mockMvc.perform(request)
                .andExpect(status().is(401))
                .andExpect(content()
                        .string("{\"status\":\"error\",\"message\":\"No such user - wrong username or password.\"}"))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
    }
}
