package com.greenfox.javatribes.javatribes.controllerTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfox.javatribes.javatribes.TestUtil;
import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Role;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.restcontrollers.KingdomRestController;
import com.greenfox.javatribes.javatribes.security.JwtTokenProvider;
import com.greenfox.javatribes.javatribes.service.LoggingService;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(KingdomRestController.class)
@WebAppConfiguration
public class KingdomRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    JwtTokenProvider jwtTokenProvider;
    @MockBean
    AuthenticationManager manager;
    @MockBean
    LoggingService loggingService;

    private User user;
    private String reqObject = ("{\"name\":\"New Kingdom\"}");
    private String expectJsonKingdom;
    private String expectJsonUpdatedKingdom;

    @Before
    public void init() throws JsonProcessingException {
        this.user = new User("user", "password");
        Kingdom userKingdom = new Kingdom("user's kingdom");
        userKingdom.setLocationX(1);
        userKingdom.setLocationY(5);
        this.user.setKingdom(userKingdom);
        this.user.addRole(Role.ROLE_ADMIN);
        userKingdom.setUser(this.user);

        Kingdom updatedKingdom = userKingdom;
//        updatedKingdom.setLocationX(10);
//        updatedKingdom.setLocationY(10);
        updatedKingdom.setName("New Kingdom");

        ObjectMapper objectMapper = new ObjectMapper();
        this.expectJsonKingdom = objectMapper.writeValueAsString(userKingdom);
        this.expectJsonUpdatedKingdom = objectMapper.writeValueAsString(updatedKingdom);
    }

    @Test
    @WithMockUser
    public void successfulGetKingdomTest() throws Exception {

        when(userService.getUserFromToken(anyObject())).thenReturn(this.user);

        RequestBuilder request = get("/kingdom")
                .contentType(TestUtil.APPLICATION_JSON_UTF8);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().string(this.expectJsonKingdom));
    }

    //    REDUNDANT ENDPOINT FOR USERS IF WE HAVE ONLY ONE KINGDOM PER USER AND WE AUTHENTICATE USER FROM JWT TOKEN
    //    SHOULD BE ACCESSED BY ADMIN ONLY
    @Test
    @WithMockUser
    public void successfulGetKingdomByUserIdTest() throws Exception {

        when(userService.getUserFromToken(anyObject())).thenReturn(this.user);
        when(userService.findById(anyLong())).thenReturn(this.user);

        RequestBuilder request = get("/kingdom/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().string(this.expectJsonKingdom));
    }

    @Test
    @WithMockUser
    public void unsuccessfulGetKingdomByUserIdTest() throws Exception {

        when(userService.getUserFromToken(anyObject())).thenReturn(this.user);
        when(userService.findById(anyLong())).thenThrow(new CustomException("UserId not found", HttpStatus.valueOf(404)));

        RequestBuilder request = get("/kingdom/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8);

        this.mockMvc.perform(request)
                .andExpect(status().is(404))
                .andExpect(content().string("{\"status\":\"error\",\"message\":\"UserId not found\"}"))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
    }

    @Test
    @WithMockUser
    public void successfulUpdateKingdomTest() throws Exception {

//        this.user.getKingdom().setLocationX(10);
//        this.user.getKingdom().setLocationY(10);
        this.user.getKingdom().setName("New Kingdom");


        when(userService.getUserFromToken(anyObject())).thenReturn(this.user);
        when(userService.updateKingdom(anyObject(), anyString())).thenReturn(this.user.getKingdom())
                .thenReturn(this.user.getKingdom());

        RequestBuilder request = patch("/kingdom")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(this.reqObject);
//                .param("locationX", "10")
//                .param("locationY", "10");

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().string(this.expectJsonUpdatedKingdom));
    }
}
