package com.greenfox.javatribes.javatribes.controllerTests;

import com.greenfox.javatribes.javatribes.TestUtil;
import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Role;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.restcontrollers.KingdomRestController;
import com.greenfox.javatribes.javatribes.security.JwtTokenProvider;
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

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyObject;
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

    private User user;
    private String expectJson;

    @Before
    public void init() {
        this.user = new User("user", "password");
        Kingdom userKingdom = new Kingdom("user's kingdom");
        userKingdom.setLocationX(1);
        userKingdom.setLocationY(5);
        this.user.setKingdom(userKingdom);
        this.user.setRoles(new ArrayList<>(Arrays.asList(Role.ROLE_USER)));
        userKingdom.setUser(this.user);
        this.expectJson = "{\"name\":\"user's kingdom\",\"locationX\":1,\"locationY\":5,\"user\":{\"id\":0," +
                "\"username\":\"user\"},\"supplies\":[],\"buildings\":[],\"troops\":[],\"kingdomId\":0}";
    }

    @Test
    @WithMockUser
    public void successfulGetKingdomTest() throws Exception {

        when(this.userService.getUserFromToken(anyObject())).thenReturn(this.user);

        RequestBuilder request = get("/kingdom")
                .contentType(TestUtil.APPLICATION_JSON_UTF8);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().string(this.expectJson));
    }

    @Test
    @WithMockUser
    public void successfulGetKingdomByUserIdTest() throws Exception {

        when(this.userService.findById(anyLong())).thenReturn(this.user);

        RequestBuilder request = get("/kingdom/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().string(this.expectJson));
    }

    @Test
    @WithMockUser
    public void unsuccessfulGetKingdomByUserIdTest() throws Exception {

        when(this.userService.findById(anyLong())).thenThrow(new CustomException("UserId not found", HttpStatus.valueOf(404)));

        RequestBuilder request = get("/kingdom/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8);

        this.mockMvc.perform(request)
                .andExpect(status().is(404))
                .andExpect(content().string("{\"status\":\"error\",\"message\":\"UserId not found\"}"))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
    }

    @Test
    @WithMockUser
    public void successfulPatchKingdomTest() throws Exception {

        this.expectJson = "{\"name\":\"newKingdom\",\"locationX\":10,\"locationY\":10,\"user\":{\"id\":0," +
                "\"username\":\"user\"},\"supplies\":[],\"buildings\":[],\"troops\":[],\"kingdomId\":0}";

        when(this.userService.getUserFromToken(anyObject())).thenReturn(this.user);

        RequestBuilder request = patch("/kingdom")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .param("name", "newKingdom")
                .param("locationX", "10")
                .param("locationY", "10");

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().string(this.expectJson));
    }

}
