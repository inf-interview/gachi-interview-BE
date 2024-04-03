package inflearn.interview.domain.DO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoComment {
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
}
