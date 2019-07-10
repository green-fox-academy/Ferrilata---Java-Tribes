package com.greenfox.javatribes.javatribes;

import com.greenfox.javatribes.javatribes.exceptions.IdentityAlreadyUsedException;
import com.greenfox.javatribes.javatribes.model.RegisterObject;
import com.greenfox.javatribes.javatribes.model.TestUtil;
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

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.doThrow;
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

    RegisterObject registerObjectWithKingdomname = new RegisterObject("Juraj", "GreenFox", "kingdom");
    RegisterObject registerObjectWithoutKingdomname = new RegisterObject("Juraj", "GreenFox", "");

    String expectedResult1 = "{\"id\":0,\"username\":\"GreenFox\",\"password\":\"Juraj\"}";
    String expectedResult2 = "{\"username\":\"GreenFox\",\"password\":\"Juraj\",\"locationX\":0,\"locationY\":0,\"id\":0,\"kingdomId\":0,\"id\":0}";

    @Test
    public void successfulRegisterUserTestWithKingdomNameInput() throws Exception {

        ResultActions resultActions = mockMvc.perform(post("/register")
                //.with(csrf())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(registerObjectWithKingdomname)))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"username\":\"GreenFox\",\"password\":\"Juraj\",\"locationX\":0,\"locationY\":0,\"id\":0,\"kingdomId\":0,\"id\":0}"))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        MvcResult result = resultActions.andReturn();
        JSONAssert.assertEquals(expectedResult1, result.getResponse().getContentAsString(), false);
        JSONAssert.assertEquals(expectedResult2, result.getResponse().getContentAsString(), true);

    }

    @Test
    public void successfulRegisterUserTestWithoutKingdomNameInput() throws Exception {

        ResultActions resultActions = mockMvc.perform(post("/register")
                //.with(csrf())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(registerObjectWithoutKingdomname)))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"username\":\"GreenFox\",\"password\":\"Juraj\",\"locationX\":0,\"locationY\":0,\"id\":0,\"kingdomId\":0,\"id\":0}"))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        MvcResult result = resultActions.andReturn();
        JSONAssert.assertEquals(expectedResult1, result.getResponse().getContentAsString(), false);
        JSONAssert.assertEquals(expectedResult2, result.getResponse().getContentAsString(), true);

    }

    @Test
    public void unsuccessfulRegisterUserTestThrowsIdentityAlreadyUsedException() throws Exception, IdentityAlreadyUsedException {

        doThrow(new IdentityAlreadyUsedException("Username already taken, please choose an other one.")).when(userService).saveUser(anyObject());

        RequestBuilder request = post("/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(registerObjectWithKingdomname));

        ResultActions resultActions = mockMvc.perform(request)
                .andExpect(status().is(409))
                .andExpect(content().string("{\"status\":\"error\",\"message\":\"Username already taken, please choose an other one.\"}"))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

    }

}


