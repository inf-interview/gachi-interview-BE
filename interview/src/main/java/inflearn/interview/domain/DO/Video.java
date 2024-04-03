package inflearn.interview.domain.DO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Video {

    Long videoId;
    LocalDateTime time;
    LocalDateTime updatedTime;
    @NotNull
    Boolean exposure;
    @NotNull
    String videoTitle;
    @NotNull
    String videoLink;
    String[] tags;
    Long like;
}