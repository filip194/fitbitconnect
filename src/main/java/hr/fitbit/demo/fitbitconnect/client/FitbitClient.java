package hr.fitbit.demo.fitbitconnect.client;

import hr.fitbit.demo.fitbitconnect.apimodel.fitbit.FitbitTokenResponse;
import hr.fitbit.demo.fitbitconnect.apimodel.fitbit.FitbitUserActivityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class FitbitClient {

    private static final Logger LOG = LoggerFactory.getLogger(FitbitClient.class);

    private static final String TOKEN_URI = "/oauth2/token";
    private static final String USER_ACTIVITIES_URI_PART_1 = "/1/user/";
    private static final String USER_ACTIVITIES_URI_PART_2 = "/activities.json";
    private static final String REDIRECT_URI_PART_1 = "http://localhost:";
    private static final String REDIRECT_URI_PART_2 = "/api/fitbit/redirect";

    @Value("${server.port}")
    private String port;
    @Value("${fitbit.client.id}")
    private String clientId;
    @Value("${fitbit.client.secret}")
    private String clientSecret;
    @Value("${fitbit.authorization.uri}")
    private String authorizationUri;
    @Value("${fitbit.base.api.uri}")
    private String baseApiUri;

    private RestTemplate restTemplate;

    public FitbitClient() {
        restTemplate = new RestTemplate();
    }

    // any user
    public ResponseEntity<FitbitTokenResponse> requestToken(String code) {

        final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.set("client_id", clientId);
        map.set("grant_type", "authorization_code");
        map.set("redirect_uri", createRedirectUri());
        map.set("code", code);

        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(map, createHeadersForToken());

        return restTemplate.exchange(
                createTokenUri(),
                HttpMethod.POST,
                request,
                FitbitTokenResponse.class
        );
    }

    // admin
    public ResponseEntity<FitbitTokenResponse> refreshUserToken(String refreshToken, int expiresIn) {

        final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.set("grant_type", "refresh_token");
        map.set("refresh_token", refreshToken);
        map.set("expires_in", String.valueOf(expiresIn));

        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(map, createHeadersForToken());

        return restTemplate.exchange(
                createTokenUri(),
                HttpMethod.POST,
                request,
                FitbitTokenResponse.class
        );
    }

    // admin
    public ResponseEntity<FitbitUserActivityResponse> getUserActivities(String fitbitUserId, String token) {

        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(createHeadersForUser(token));

        return restTemplate.exchange(
                createUserActivitiesUri(fitbitUserId),
                HttpMethod.GET,
                request,
                FitbitUserActivityResponse.class
        );
    }

    private String createUserActivitiesUri(String fitbitUsername) {
        return baseApiUri + USER_ACTIVITIES_URI_PART_1 + fitbitUsername + USER_ACTIVITIES_URI_PART_2;
    }

    private String createTokenUri() {
        return baseApiUri + TOKEN_URI;
    }

    private String createRedirectUri() {
        return REDIRECT_URI_PART_1 + port + REDIRECT_URI_PART_2;
    }

    private HttpHeaders createHeadersForToken() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret, StandardCharsets.UTF_8);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    private HttpHeaders createHeadersForUser(String token) {
        final List<MediaType> acceptedMediaTypes = new ArrayList<>(1);
        acceptedMediaTypes.add(MediaType.ALL);

        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setAccept(acceptedMediaTypes);
        return headers;
    }
}

