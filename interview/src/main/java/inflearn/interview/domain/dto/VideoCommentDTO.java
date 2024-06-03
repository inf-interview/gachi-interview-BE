package inflearn.interview.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class VideoCommentDTO implements BaseDTO{

    public interface create{}
    public interface patch{}

    public interface delete{}

    @NotNull(groups = patch.class)
    Long commentId;
    @NotNull(groups = {create.class, patch.class, delete.class})
    Long userId;
    Long videoId;
    String userName;
    @NotNull(groups = {create.class, patch.class})
    String content;

    LocalDateTime time;
    LocalDateTime updatedTime;
    Long like;
}