package com.greenfox.javatribes.javatribes.controllerTests;

import com.greenfox.javatribes.javatribes.TestUtil;
import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.restcontrollers.KingdomRestController;
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
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    Kingdom testKingdom = new Kingdom("Mordor", 1, 5);
    User testUser = new User("Juraj", "GreenFox", testKingdom);
    String testToken = "This is my secret sacred Token!";

    @Test
    @WithMockUser
    public void successfulGetKingdomTest() throws Exception {

        //when(userService.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(anyObject())))).thenReturn(testUser);
        when(userService.identifyUserFromJWTToken(anyObject())).thenReturn(testUser);

        RequestBuilder request = get("/kingdom")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(testToken));

        ResultActions resultActions = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().string("{\"id\":0,\"username\":\"Juraj\",\"password\":\"GreenFox\",\"name\":\"Mordor\",\"locationX\":1,\"locationY\":5,\"supplies\":[],\"buildings\":[],\"troops\":[],\"kingdomId\":0}"));

    }

    @Test
    @WithMockUser
    public void successfulGetKingdomByUserIdTest() throws Exception {

        when(userService.findById(anyLong())).thenReturn(testUser);

        RequestBuilder request = get("/kingdom/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8);

        ResultActions resultActions = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("{\"name\":\"Mordor\",\"locationX\":1,\"locationY\":5,\"supplies\":[],\"buildings\":[],\"troops\":[],\"kingdomId\":0}"))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

    }

    @Test
    @WithMockUser
    public void unsuccessfulGetKingdomByUserIdTest() throws Exception {

        when(userService.findById(anyLong())).thenThrow(new CustomException("UserId not found", HttpStatus.valueOf(404)));

        RequestBuilder request = get("/kingdom/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8);

        ResultActions resultActions = mockMvc.perform(request)
                .andExpect(status().is(404))
                .andExpect(content().string("{\"status\":\"error\",\"message\":\"UserId not found\"}"))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

    }

    @Test
    @WithMockUser
    public void successfulPutKingdomTest() throws Exception {

        when(userService.identifyUserKingdomFromJWTToken(anyObject())).thenReturn(testKingdom);
        //when(userService.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(anyObject())))).thenReturn(testUser);

        RequestBuilder request = put("/kingdom")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .param("name", "Juraj")
                .param("locationX", "10")
                .param("locationY", "10");

        ResultActions resultActions = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("{\"name\":\"Juraj\",\"locationX\":10,\"locationY\":10,\"supplies\":[],\"buildings\":[],\"troops\":[],\"kingdomId\":0}"))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

    }

}
