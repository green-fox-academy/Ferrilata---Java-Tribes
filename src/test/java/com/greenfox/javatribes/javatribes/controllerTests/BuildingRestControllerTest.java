package com.greenfox.javatribes.javatribes.controllerTests;

import com.greenfox.javatribes.javatribes.model.*;
import com.greenfox.javatribes.javatribes.restcontrollers.BuildingRestController;
import com.greenfox.javatribes.javatribes.security.JwtTokenProvider;
import com.greenfox.javatribes.javatribes.service.BuildingService;
import com.greenfox.javatribes.javatribes.service.UserService;
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
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BuildingRestController.class)
@WebAppConfiguration
public class BuildingRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    BuildingService buildingService;
    @MockBean
    JwtTokenProvider jwtTokenProvider;
    @MockBean
    AuthenticationManager manager;

    private Building townhall = new Building("townhall");
    private Kingdom testKingdom = new Kingdom("User's Kingdom");
    private User user = new User("user", "password", testKingdom);

    @Test
    @WithMockUser
    public void getKingdomBuildingsTest_basic() throws Exception {

        this.testKingdom.addBuilding(this.townhall);
        when(userService.getUserFromToken(anyObject())).thenReturn(user);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/kingdom/buildings")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":0,\"type\":\"townhall\",\"level\":1,\"hp\":0}]"))
                .andReturn();
    }
}
