package hr.fitbit.demo.fitbitconnect.controller;

import hr.fitbit.demo.fitbitconnect.TestApplication;
import hr.fitbit.demo.fitbitconnect.apimodel.fitbit.FitbitTokenResponse;
import hr.fitbit.demo.fitbitconnect.apimodel.fitbit.FitbitUserActivityResponse;
import hr.fitbit.demo.fitbitconnect.fixture.FitbitFixture;
import hr.fitbit.demo.fitbitconnect.service.FitbitService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ContextConfiguration(classes = TestApplication.class)
@WebMvcTest(FitbitController.class)
public class FitbitControllerTest extends AbstractControllerTest {

    @MockBean
    private FitbitService fitbitService;

    @Test
    @WithMockUser(roles = AUTHENTICATED_USER_ROLE)
    public void shouldRegisterUserOnFitbit() throws Exception {
        final String code = "test-code";

        when(fitbitService.getCode()).thenReturn(code);
        when(fitbitService.registerUserOnFitbit(any())).thenReturn(true);
        final MockHttpServletResponse response = mvc.perform(get("/api/fitbit/redirect?code=")
                .accept(MediaType.ALL))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    public void shouldRefreshUserAccessToken() throws Exception {
        final UUID userId = UUID.randomUUID();
        final FitbitTokenResponse fitbitTokenResponse = FitbitFixture.createFitbitTokenResponse();

        when(fitbitService.getRefreshToken(any())).thenReturn(fitbitTokenResponse);
        final MockHttpServletResponse response = mvc.perform(get("/api/fitbit/refresh-token/" + userId)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(fitbitTokenResponse));
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    public void shouldGetUserActivities() throws Exception {
        final UUID userId = UUID.randomUUID();
        final FitbitUserActivityResponse fitbitUserActivityResponse = FitbitFixture.createFitbitActivityResponse();

        when(fitbitService.getUserActivities(any())).thenReturn(fitbitUserActivityResponse);
        final MockHttpServletResponse response = mvc.perform(get("/api/fitbit/activities/" + userId)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(fitbitUserActivityResponse));
    }
}

