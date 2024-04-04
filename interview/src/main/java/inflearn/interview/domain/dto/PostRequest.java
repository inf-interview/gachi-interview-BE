package inflearn.interview.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {

    @NotBlank
    private String postTitle;

    private String tag;

    @NotBlank
    private String content;

    @NotNull
    private String category;


}
