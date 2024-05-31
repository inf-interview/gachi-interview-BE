package inflearn.interview.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class VideoCommentDTO {

    Long commentId;
    Long userId;
    Long videoId;
    String userName;
    String content;

    LocalDateTime time;
    LocalDateTime updatedTime;
    Long like;
}