package com.greenfox.javatribes.javatribes.controllerTests;

import com.greenfox.javatribes.javatribes.TestUtil;
import com.greenfox.javatribes.javatribes.dto.RegisterObject;
import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.restcontrollers.UserRestController;
import com.greenfox.javatribes.javatribes.security.JwtTokenProvider;
import com.greenfox.javatribes.javatribes.service.LoggingService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserRestController.class)
public class UserRestControllerTestRegister {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    AuthenticationManager manager;
    @MockBean
    LoggingService loggingService;

    private RegisterObject registerObject = new RegisterObject("user", "password", "");

    @Test
    @WithMockUser
    public void successfulRegisterUserTest() throws Exception {

        User newUser = new User(registerObject.getUsername(), registerObject.getPassword());
        when(this.userService.register(anyObject())).thenReturn(newUser);

        String reqContent = ("{\"username\":\"username\",\"password\":\"password\", \"kingdom\":\"\"}");
        RequestBuilder request = post("/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(reqContent);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":0,\"username\":\"user\"}"))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
    }


//    @Test
//    public void successfulRegisterUserTest() throws Exception {
//
//        ResultActions resultActions = mockMvc.perform(post("/register")
//                .contentType(TestUtil.APPLICATION_JSON_UTF8)
//                .content(TestUtil.convertObjectToJsonBytes(registerObject)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("{\"id\":0,\"username\":\"user\"}"))
//                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
//
//        MvcResult result = resultActions.andReturn();
//        String expectedResult = "{\"id\":0,\"username\":\"user\"}";
//        JSONAssert.assertEquals(expectedResult, result.getResponse().getContentAsString(), false);
//    }

    @Test
    public void unsuccessfulRegisterUserTestThrowsIdentityAlreadyUsedException() throws Exception {

        String errMessage = "Username already taken, please choose an other one.";
        doThrow(new CustomException(errMessage, HttpStatus.valueOf(409))).when(userService).register(anyObject());

        RequestBuilder request = post("/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(registerObject));

        mockMvc.perform(request)
                .andExpect(status().is(409))
                .andExpect(content().string("{\"status\":\"error\",\"message\":" +
                        "\"Username already taken, please choose an other one.\"}"))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
    }
}

