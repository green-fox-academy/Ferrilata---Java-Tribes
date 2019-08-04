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

    private User user;
    private String expectJson;
    private String expectJsonPatch;

    @Before
    public void init() {
        this.user = new User("user", "password");
        Kingdom userKingdom = new Kingdom("user's kingdom");
        userKingdom.setLocationX(1);
        userKingdom.setLocationY(5);
        this.user.setKingdom(userKingdom);
        this.user.addRole(Role.ROLE_ADMIN);
        userKingdom.setUser(this.user);
        long goldUpdateAt = (userKingdom.getSupplies().get(0).getUpdateAt()).getTime();
        long foodUpdateAt = userKingdom.getSupplies().get(0).getUpdateAt().getTime();
        String expectJsonSupplies = "[{\"id\":0,\"type\":\"gold\",\"amount\":1000,\"generation\":0," +
                "\"updateAt\":" + goldUpdateAt + "},{\"id\":0,\"type\":\"food\",\"amount\":1000," +
                "\"generation\":0,\"updateAt\":" + foodUpdateAt + "}]";

        this.expectJson = "{\"name\":\"user's kingdom\",\"locationX\":1,\"locationY\":5,\"user\":{\"id\":0," +
                "\"username\":\"user\"},\"supplies\":" + expectJsonSupplies + ",\"buildings\":[],\"troops\":[],\"kingdomId\":0}";

        this.expectJsonPatch = "{\"name\":\"New Kingdom\",\"locationX\":10,\"locationY\":10,\"user\":{\"id\":0," +
                "\"username\":\"user\"},\"supplies\":" + expectJsonSupplies + ",\"buildings\":[],\"troops\":[],\"kingdomId\":0}";
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

    //    REDUNDANT ENDPOINT FOR USERS IF WE HAVE ONLY ONE KINGDOM PER USER AND WE AUTHENTICATE USER FROM JWT TOKEN
    //    SHOULD BE ACCESSED BY ADMIN ONLY
    @Test
    @WithMockUser
    public void successfulGetKingdomByUserIdTest() throws Exception {

        when(this.userService.getUserFromToken(anyObject())).thenReturn(this.user);
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

        when(this.userService.getUserFromToken(anyObject())).thenReturn(this.user);
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
    public void successfulUpdateKingdomTest() throws Exception {

        this.user.getKingdom().setLocationX(10);
        this.user.getKingdom().setLocationY(10);
        this.user.getKingdom().setName("New Kingdom");


        when(this.userService.getUserFromToken(anyObject())).thenReturn(this.user);
        when(this.userService.updateKingdom(anyObject(), anyString(), anyInt(), anyInt())).thenReturn(this.user.getKingdom())
                .thenReturn(this.user.getKingdom());

        RequestBuilder request = patch("/kingdom")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .param("name", "New Kingdom")
                .param("locationX", "10")
                .param("locationY", "10");

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().string(this.expectJsonPatch));
    }
}
