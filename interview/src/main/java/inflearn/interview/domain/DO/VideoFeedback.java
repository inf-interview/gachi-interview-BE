package inflearn.interview.domain.DO;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class VideoFeedback {
    Long userId;
    String userName;
    @NotNull
    Long videoId;
    LocalDateTime time;
    LocalDateTime updatedTime;
    @NotNull
    Boolean exposure;
    @NotNull
    String videoTitle;
    @NotNull
    String videoLink;
    String rawTags;
    String[] tags;
    String content;
    String feedback;
}
