package hr.fitbit.demo.fitbitconnect.client;

import hr.fitbit.demo.fitbitconnect.apimodel.fitbit.FitbitTokenResponse;
import hr.fitbit.demo.fitbitconnect.apimodel.fitbit.FitbitUserActivityResponse;
import hr.fitbit.demo.fitbitconnect.fixture.FitbitFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class FitbitClientTest {

    @MockBean
    private FitbitClient fitbitClient;

    @Test
    public void shouldRequestToken() {

        final FitbitTokenResponse tokenResponse = FitbitFixture.createFitbitTokenResponse();
        final ResponseEntity<FitbitTokenResponse> response = new ResponseEntity<>(tokenResponse, HttpStatus.OK);
        when(fitbitClient.requestToken(any())).thenReturn(response);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getAccessToken()).isEqualTo(tokenResponse.getAccessToken());
        assertThat(response.getBody().getRefreshToken()).isEqualTo(tokenResponse.getRefreshToken());
        assertThat(response.getBody().getUserId()).isEqualTo(tokenResponse.getUserId());
        assertThat(response.getBody().getExpiresIn()).isEqualTo(tokenResponse.getExpiresIn());
        assertThat(response.getBody().getTokenType()).isEqualTo(tokenResponse.getTokenType());
    }

    @Test
    public void shouldRefreshUserToken() {

        final FitbitTokenResponse tokenResponse = FitbitFixture.createFitbitTokenResponseRefreshed();
        final ResponseEntity<FitbitTokenResponse> response = new ResponseEntity<>(tokenResponse, HttpStatus.OK);

        when(fitbitClient.refreshUserToken(anyString(), anyInt())).thenReturn(response);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getAccessToken()).isEqualTo(tokenResponse.getAccessToken());
        assertThat(response.getBody().getRefreshToken()).isEqualTo(tokenResponse.getRefreshToken());
        assertThat(response.getBody().getUserId()).isEqualTo(tokenResponse.getUserId());
        assertThat(response.getBody().getExpiresIn()).isEqualTo(tokenResponse.getExpiresIn());
        assertThat(response.getBody().getTokenType()).isEqualTo(tokenResponse.getTokenType());
    }

    @Test
    public void shouldGetUserActivities() {

        final FitbitUserActivityResponse activityResponse = FitbitFixture.createFitbitActivityResponse();
        final ResponseEntity<FitbitUserActivityResponse> response = new ResponseEntity<>(activityResponse, HttpStatus.OK);

        when(fitbitClient.getUserActivities(anyString(), anyString())).thenReturn(response);

        // tracker
        assertThat(response.getBody().getLifetime().getTracker().getActiveScore()).isEqualTo(
                activityResponse.getLifetime().getTracker().getActiveScore());
        assertThat(response.getBody().getLifetime().getTracker().getCaloriesOut()).isEqualTo(
                activityResponse.getLifetime().getTracker().getCaloriesOut());
        assertThat(response.getBody().getLifetime().getTracker().getDistance()).isEqualTo(
                activityResponse.getLifetime().getTracker().getDistance());
        assertThat(response.getBody().getLifetime().getTracker().getSteps()).isEqualTo(
                activityResponse.getLifetime().getTracker().getSteps());

        // total
        assertThat(response.getBody().getLifetime().getTotal().getActiveScore()).isEqualTo(
                activityResponse.getLifetime().getTotal().getActiveScore());
        assertThat(response.getBody().getLifetime().getTotal().getCaloriesOut()).isEqualTo(
                activityResponse.getLifetime().getTotal().getCaloriesOut());
        assertThat(response.getBody().getLifetime().getTotal().getDistance()).isEqualTo(
                activityResponse.getLifetime().getTotal().getDistance());
        assertThat(response.getBody().getLifetime().getTotal().getSteps()).isEqualTo(
                activityResponse.getLifetime().getTotal().getSteps());
    }
}

