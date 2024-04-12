package inflearn.interview.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostCommentDTO {

    private Long commentId;

    private Long userId;

    private String username;

    private String content;

    private LocalDateTime createdAt;

}
