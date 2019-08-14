package com.greenfox.javatribes.javatribes.controllerTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfox.javatribes.javatribes.model.*;
import com.greenfox.javatribes.javatribes.restcontrollers.SupplyRestController;
import com.greenfox.javatribes.javatribes.security.JwtTokenProvider;
import com.greenfox.javatribes.javatribes.service.LoggingService;
import com.greenfox.javatribes.javatribes.service.SupplyService;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SupplyRestController.class)
@WebAppConfiguration
public class SupplyRestControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    SupplyService supplyService;
    @MockBean
    JwtTokenProvider jwtTokenProvider;
    @MockBean
    AuthenticationManager manager;
    @MockBean
    LoggingService loggingService;

    private User user;
    private String expectJsonSupplies;

    @Before
    public void init() throws JsonProcessingException {
        this.user = new User("user", "password");
        Kingdom userKingdom = new Kingdom("user's kingdom");
        this.user.setKingdom(userKingdom);

        ObjectMapper objectMapper = new ObjectMapper();
        this.expectJsonSupplies = objectMapper.writeValueAsString(userKingdom.getSupplies());

    }

    @Test
    @WithMockUser
    public void getKingdomSuppliesTest_basic() throws Exception {

        when(userService.getUserFromToken(anyObject())).thenReturn(this.user);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/kingdom/supplies")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(this.expectJsonSupplies))
                .andReturn();
    }
}
