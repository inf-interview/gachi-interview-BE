package inflearn.interview.user.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

}
