package ma.eshop.usersapi.services;

import ma.eshop.usersapi.models.Role;
import ma.eshop.usersapi.models.User;
import ma.eshop.usersapi.repositories.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringJUnit4ClassRunner.class)
class UsersServiceTest {
    @Mock
    private UsersRepository usersRepository;

    @Mock
    private JwtUtilService jwtUtilService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private MyUserDetailsService userDetailsService;

    @InjectMocks
    private UsersService usersService;

    @Test
    void getByEmail() {
    }

    @Test
    void createUser() {
    }

    @Test
    void existsByEmail() {
    }

    @Test
    void findById() {
        User user = new User();
        user.getId();
        user.setEmail("bkn@sqli.com");
        user.setLastName("Boukouyen");
        user.setFirstName("Tariq");
        user.setRole(Role.USER);
        user.setPassword("a");

        Mockito.when(usersService.existsByEmail(any(String.class))).thenReturn(false);
    }

    @Test
    void findAll() {
    }

    @Test
    void alterAccountStatusOfUserWithId() {
    }

    @Test
    void blockedUsersNumber() {
    }

    @Test
    void count() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void noUsersInDatabase() {
    }
}
