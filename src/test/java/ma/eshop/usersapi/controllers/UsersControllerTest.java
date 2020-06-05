package ma.eshop.usersapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.eshop.usersapi.models.Role;
import ma.eshop.usersapi.models.User;
import ma.eshop.usersapi.services.UsersService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
class UsersControllerTest {
    @Mock
    private UsersService usersService;

    @InjectMocks
    private UsersController usersController;

    private MockMvc mockMvc;


    private ObjectMapper objectMapper= new ObjectMapper();

    @BeforeEach
    void setUp() {
        Mockito.when(usersService.existsByEmail(any(String.class))).thenReturn(false);

        usersController = new UsersController();
        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
    }

    @Before

    @Test
    void login() throws Exception {
        /*mockMvc.perform(
                post("/users/0")
        ).andExpect(status().isOk())
         .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].firstName", Matchers.is("Tariq")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].lastName", Matchers.is("BOUKOUYEN")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].address.city", Matchers.is("Sefrou")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].address.streetName", Matchers.is("Niyami")));*/
    }

    @Test
    void signOn() throws JsonProcessingException, Exception {

        User user = new User();
        user.setEmail("bkn@sqli.com");
        user.setLastName("Boukouyen");
        user.setFirstName("Tariq");
        user.setRole(Role.USER);
        user.setPassword("a");
        Mockito.when(usersService.existsByEmail(any(String.class))).thenReturn(false);
        String userInJsonFormat = objectMapper.writeValueAsString(user);
        MvcResult mvcResult = mockMvc.perform(post("/users/signup").content(userInJsonFormat).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        String resultContent = mvcResult.getResponse().getContentAsString();
        ResponseEntity responseEntity = objectMapper.readValue(resultContent, ResponseEntity.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);

    }

    @Test
    void updateUserData() {
    }

    @Test
    void getUsers() {
       /* mockMvc.perform(
                MockMvcRequestBuilders.get("/users/login")
        ).andExpect(MockMvcResultMatchers.)*/
    }

    @Test
    void alterAccountStatusOfUserWithId() {
    }

    @Test
    void search() {
    }

    @Test
    void getNumberOfBlockedUser() {
    }

    @Test
    void count() {
    }

    @Test
    void delete() {
    }

    @Test
    void testLogin() {
    }

    @Test
    void testSignOn() {
    }

    @Test
    void testGetConnectedUser() {
    }

    @Test
    void testUpdateUserData() {
    }

    @Test
    void testGetUsers() {
    }

    @Test
    void testAlterAccountStatusOfUserWithId() {
    }

    @Test
    void testSearch() {
    }

    @Test
    void testGetNumberOfBlockedUser() {
    }

    @Test
    void testCount() {
    }

    @Test
    void testDelete() {
    }
}
