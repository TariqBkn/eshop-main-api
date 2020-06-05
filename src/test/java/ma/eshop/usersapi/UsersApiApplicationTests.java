package ma.eshop.usersapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;

@SpringBootTest
@WebMvcTest
class UsersApiApplicationTests {

    @Inject
    private MockMvc mockMvc;

    @Test
    void contextLoads() {

    }

}
