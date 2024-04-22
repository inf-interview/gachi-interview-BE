package inflearn.interview.service;

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
public class KakaoProvider {

    @Value("${spring.kakao.client_id}")
    String clientId;

    @Value("${spring.kakao.client_secret}")
    String clientSecret;

    private final String GET_TOKEN_URL = "https://kauth.kakao.com/oauth/token";

    public String[] getAccessToken(String code) {

        //엑세스 토큰 폼타입으로 요청
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", "http://localhost:8080/user/kakao/login"); // 리다이렉트 URL
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> requestEntity1 = new HttpEntity<>(params, headers1);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<KakaoTokenResponse> tokenResponse = restTemplate.postForEntity(GET_TOKEN_URL, requestEntity1, KakaoTokenResponse.class);

        if (tokenResponse.getStatusCode() == HttpStatus.OK && tokenResponse.getBody() != null) {
            log.info("accessToken={} refreshToken={}", tokenResponse.getBody().getAccessToken(), tokenResponse.getBody().getRefreshToken());
            return new String[]{tokenResponse.getBody().getAccessToken(), tokenResponse.getBody().getRefreshToken()};
        } else {
            return null;
        }
    }

    //발급받은 accessToken으로 유저 정보 가져오기 - JSON 반환
    public String getKakaoInfo(String accessToken) {

        String requestInfoUrl = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers2 = new HttpHeaders();
        headers2.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers2.setBearerAuth(accessToken);
        HttpEntity<?> requestEntity2 = new HttpEntity<>(headers2);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForEntity(requestInfoUrl, requestEntity2, String.class).getBody();
    }

}