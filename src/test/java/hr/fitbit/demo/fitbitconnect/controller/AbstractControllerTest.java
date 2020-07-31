package hr.fitbit.demo.fitbitconnect.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.fitbit.demo.fitbitconnect.dao.RoleRepository;
import hr.fitbit.demo.fitbitconnect.dao.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebClient
public abstract class AbstractControllerTest {

    protected static final String ADMIN_ROLE = "ADMIN";
    protected static final String MODERATOR_ROLE = "MODERATOR";
    protected static final String USER_ROLE = "USER";
    protected static final String AUTHENTICATED_USER_ROLE = "AUTHENTICATED_USER";

    @Autowired
    protected MockMvc mvc;

    protected ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

}

