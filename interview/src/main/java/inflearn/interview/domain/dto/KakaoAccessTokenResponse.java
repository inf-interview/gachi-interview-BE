package inflearn.interview.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoAccessTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;
}
