package com.greenfox.javatribes.javatribes;

import com.greenfox.javatribes.javatribes.exceptions.UserIdNotFoundException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.TestUtil;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.restcontrollers.KingdomRestController;
import com.greenfox.javatribes.javatribes.security.JWTTokenUtil;
import com.greenfox.javatribes.javatribes.service.KingdomService;
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
    private KingdomService kingdomService;
    @MockBean
    private UserService userService;
    @MockBean
    private User user;
    @MockBean
    private JWTTokenUtil jwtTokenUtil;

    @Test
    public void successfulGetKingdomTest() throws Exception {

        Kingdom testKingdom = new Kingdom ("Mordor");
        testKingdom.setLocationX(1);
        testKingdom.setLocationY(5);

        User newUser = new User("Juraj", "GreenFox",new Kingdom("Gondor"));
        when(userService.findByUsername(user.getUsername()).getKingdom()).thenReturn(testKingdom);

        RequestBuilder request = get("/kingdom")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(newUser));

        ResultActions resultActions = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().string("")); //should return selected kingdom object by token

    }

    @Test
    public void successfulGetKingdomByUserIdTest() throws Exception {

        Kingdom testKingdom = new Kingdom ("Mordor");
        testKingdom.setLocationX(1);
        testKingdom.setLocationY(5);

        when(userService.findById(anyLong())).thenReturn(new User("Juraj", "GreenFox",testKingdom));

        RequestBuilder request = get("/kingdom/{id}",1L)
                                    .contentType(TestUtil.APPLICATION_JSON_UTF8);

        ResultActions resultActions = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("{\"locationX\":1,\"locationY\":5,\"id\":0,\"kingdomId\":0}"))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

    }

    @Test
    public void unsuccessfulGetKingdomByIdTest() throws Exception {

        when(userService.findById(anyLong())).thenThrow(new UserIdNotFoundException("UserId not found"));

        RequestBuilder request = get("/kingdom/{id}",1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8);

        ResultActions resultActions = mockMvc.perform(request)
                .andExpect(status().is(404))
                .andExpect(content().string("{\"status\":\"error\",\"message\":\"UserId not found\"}"))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

    }

    @Test
    public void successfulPutKingdomTest() throws Exception {

        Kingdom kingdom;

        RequestBuilder request = put("/kingdom")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jwtTokenUtil))
                .param("name","Juraj")
                .param("locationX","10")
                .param("locationY","10");

        ResultActions resultActions = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("")) //should return selected kingdom object by token in modified form
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

    }

}
