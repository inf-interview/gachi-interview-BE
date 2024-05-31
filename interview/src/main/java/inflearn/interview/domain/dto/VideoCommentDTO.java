package inflearn.interview.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class VideoCommentDTO {

    public interface create{}
    public interface patch{}

    @NotNull(groups = patch.class)
    Long commentId;
    @NotNull(groups = {create.class, patch.class})
    Long userId;
    Long videoId;
    String userName;
    @NotNull(groups = {create.class, patch.class})
    String content;

    LocalDateTime time;
    LocalDateTime updatedTime;
    Long like;
}