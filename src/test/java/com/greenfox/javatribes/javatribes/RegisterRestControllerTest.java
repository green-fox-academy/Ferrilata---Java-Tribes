package com.greenfox.javatribes.javatribes;


import com.greenfox.javatribes.javatribes.exceptions.IdentityAlreadyUsedException;
import com.greenfox.javatribes.javatribes.model.TestUtil;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.restcontrollers.RegisterRestController;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf();

@RunWith(SpringRunner.class)
@WebMvcTest(RegisterRestController.class)
public class RegisterRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void successfulRegisterUserTest () throws Exception {

        User user = new User("Juraj","GreenFox");

        //ObjectMapper mapper = new ObjectMapper();
        //String expectedResponseString = mapper.writeValueAsString(expectedResponse);
        //String userToJSON = mapper.writeValueAsString(user);

        //when(userService.saveUser(any(User.class))).thenReturn(user);

        String expectedResult1 = "{\"username\":\"Juraj\",\"password\":\"GreenFox\"}";
        String expectedResult2 = "{\"id\":0,\"username\":\"Juraj\",\"password\":\"GreenFox\",\"kingdom\":null}";

        ResultActions resultActions = mockMvc.perform(post("/register")
                //.with(csrf())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user)))
                .andExpect(status().isOk())
                //.andExpect((ResultMatcher) jsonPath("$.username", is("Juraj")))
                .andExpect(content().string("{\"id\":0,\"username\":\"Juraj\",\"password\":\"GreenFox\",\"kingdom\":null}"))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        MvcResult result = resultActions.andReturn();
        JSONAssert.assertEquals(expectedResult1,result.getResponse().getContentAsString(),false);
        JSONAssert.assertEquals(expectedResult2,result.getResponse().getContentAsString(),true);

        /*ResultActions resultActions = mockMvc.perform(post("/register")

                .param("user", userToJSON))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();*/

        //System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void unsuccessfulRegisterUserTestWithUsernameAlreadyUsedException () throws Exception, IdentityAlreadyUsedException {

        User user = new User("Juraj","GreenFox");

        doThrow(new IdentityAlreadyUsedException("Username already taken, please choose an other one.")).doNothing().when(userService).saveUser(user);

        when(userService.existsByUsername("Juraj")).thenThrow(new IdentityAlreadyUsedException("Username already taken, please choose an other one."));

        RequestBuilder request = post("/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user));

        ResultActions resultActions = mockMvc.perform(request)
                .andExpect(status().is(409))
                .andExpect(content().string("{\"status\":\"error\",\"message\":\"Username already taken, please choose an other one.\"}"))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

    }

}


