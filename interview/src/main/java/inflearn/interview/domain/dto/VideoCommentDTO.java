package inflearn.interview.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class VideoCommentDTO {
    @NotNull
    Long commentId;
    @NotNull
    Long userId;
    @NotNull
    Long videoId;
    String userName;
    @NotNull
    String content;
    @NotNull
    LocalDateTime time;
    LocalDateTime updatedTime;
    Long like;
}