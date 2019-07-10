package com.greenfox.javatribes.javatribes;

import com.greenfox.javatribes.javatribes.exceptions.UserIdNotFoundException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.TestUtil;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.restcontrollers.KingdomRestController;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(KingdomRestController.class)

public class KingdomRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    Kingdom testKingdom = new Kingdom("Mordor",1,5);
    User testUser = new User("Juraj", "GreenFox",testKingdom);

    //this end point should eventually return kingdom of the active (logged in) user (based on token verification?)
    @Test
    public void successfulGetKingdomTest() throws Exception {

        when(userService.findByUsername(anyString())).thenReturn(testUser);

        RequestBuilder request = get("/kingdom")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(testUser));

        ResultActions resultActions = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().string("{\"locationX\":1,\"locationY\":5,\"id\":0,\"kingdomId\":0}"));

    }

    @Test
    public void successfulGetKingdomByUserIdTest() throws Exception {

        when(userService.findById(anyLong())).thenReturn(testUser);

        RequestBuilder request = get("/kingdom/{id}",1L)
                                    .contentType(TestUtil.APPLICATION_JSON_UTF8);

        ResultActions resultActions = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("{\"locationX\":1,\"locationY\":5,\"id\":0,\"kingdomId\":0}"))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

    }

    @Test
    public void unsuccessfulGetKingdomByUserIdTest() throws Exception {

        when(userService.findById(anyLong())).thenThrow(new UserIdNotFoundException("UserId not found"));

        RequestBuilder request = get("/kingdom/{id}",1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8);

        ResultActions resultActions = mockMvc.perform(request)
                .andExpect(status().is(404))
                .andExpect(content().string("{\"status\":\"error\",\"message\":\"UserId not found\"}"))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

    }

    //this end point should eventually edit name and location of kingdom of the active (logged in) user (based on token verification?)
    @Test
    public void successfulPutKingdomTest() throws Exception {

        when(userService.findByUsername(anyString())).thenReturn(testUser);

        RequestBuilder request = put("/kingdom")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(testUser))
                .param("name","Juraj")
                .param("locationX","10")
                .param("locationY","10");

        ResultActions resultActions = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("{\"locationX\":10,\"locationY\":10,\"id\":0,\"kingdomId\":0}"))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

    }

}
