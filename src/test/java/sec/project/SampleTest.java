package sec.project;

import javax.servlet.Filter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sec.project.repository.SignupRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private MockHttpSession mockSession;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).addFilters(springSecurityFilterChain).build();
    }

    @Test
    public void cannotChangePasswordWithoutCrsfToken() throws Throwable {
        // login
        mockMvc.perform(post("/login").session(mockSession).param("username", "ted").param("password", "ted").with(
                SecurityMockMvcRequestPostProcessors.csrf())).andReturn();
        // change password (no token), should not change password
        mockMvc.perform(post("/password").session(mockSession).param("password", "newted")).andReturn();

        // logout
        mockMvc.perform(post("/logout").session(mockSession).with(SecurityMockMvcRequestPostProcessors.csrf())).andReturn();
        mockSession.clearAttributes();

        // login should fail due to wrong password
        MvcResult res = mockMvc.perform(post("/login").session(mockSession).param("username", "ted").param("password", "newted").with(
                SecurityMockMvcRequestPostProcessors.csrf())).andReturn();
        assertTrue(res.getResponse().getRedirectedUrl().contains("login"));

        // login should succeed with old password
        mockMvc.perform(post("/login").session(mockSession).param("username", "ted").param("password", "ted").with(
                SecurityMockMvcRequestPostProcessors.csrf())).andReturn();
    }


}
