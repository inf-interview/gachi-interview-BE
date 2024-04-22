package inflearn.interview.service;

import inflearn.interview.domain.dto.GoogleTokenResponse;
import inflearn.interview.domain.dto.KakaoTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class GoogleProvider {

    @Value("${spring.google.client_id}")
    private String clientId;

    @Value("${spring.google.client_secret}")
    private String clientSecret;

    private final String GET_TOKEN_URL = "https://oauth2.googleapis.com/token";

    public String[] getAccessToken(String code) {
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", "http://localhost:8080/user/google/login"); // 리다이렉트 URL
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> requestEntity1 = new HttpEntity<>(params, headers1);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<GoogleTokenResponse> tokenResponse = restTemplate.postForEntity(GET_TOKEN_URL, requestEntity1, GoogleTokenResponse.class);

        if (tokenResponse.getStatusCode() == HttpStatus.OK && tokenResponse.getBody() != null) {
            log.info("accessToken={} refreshToken={}", tokenResponse.getBody().getAccessToken(), tokenResponse.getBody().getRefreshToken());
            return new String[]{tokenResponse.getBody().getAccessToken(), tokenResponse.getBody().getRefreshToken()};
        } else {
            return null;
        }
    }

    public String getGoogleInfo(String accessToken) {
        String requestInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(requestInfoUrl, HttpMethod.GET, requestEntity, String.class).getBody();
    }

    public String getAccessTokenByRefreshToken(String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("client_id", clientId);
        params.add("refresh_token", refreshToken);
        params.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<KakaoTokenResponse> tokenResponse = restTemplate.postForEntity(GET_TOKEN_URL, requestEntity, KakaoTokenResponse.class);

        if (tokenResponse.getStatusCode() == HttpStatus.OK && tokenResponse.getBody() != null) {
            return tokenResponse.getBody().getAccessToken();
        }
        return null;
    }
}
