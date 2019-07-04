package com.greenfox.javatribes.javatribes;

import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.TestUtil;
import com.greenfox.javatribes.javatribes.security.JWTTokenUtil;
import com.greenfox.javatribes.javatribes.service.KingdomService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class KingdomRestControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private KingdomService kingdomService;
    @MockBean
    private JWTTokenUtil jwtTokenUtil;

    @Test
    public void successfulGetKingdomTest() throws Exception {

        Kingdom kingdom;

        RequestBuilder request = get("/kingdom")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jwtTokenUtil));

        ResultActions resultActions = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().string("")); //should return selected kingdom object by token

    }

    @Test
    public void successfulGetKingdomByIdTest() throws Exception {

        Kingdom kingdom;

        RequestBuilder request = get("/kingdom")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .param("userId","1");

        ResultActions resultActions = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("")) //should return kingdom object by id
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

    }

    //Test returning 404 error message endpoint for trying to find non-existing kingdom will be created

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
