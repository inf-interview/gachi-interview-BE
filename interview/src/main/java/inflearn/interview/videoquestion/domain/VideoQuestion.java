package inflearn.interview.videoquestion.domain;

import inflearn.interview.question.domain.Question;
import inflearn.interview.video.domain.Video;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VideoQuestion {
    private Long id;
    private Video video;
    private Long questionId;
    private String question;

    @Builder
    public VideoQuestion(Long id, Video video, Long questionId, String question) {
        this.id = id;
        this.video = video;
        this.questionId = questionId;
        this.question = question;
    }

    public static VideoQuestion from(Video video, Question question) {
        return VideoQuestion.builder()
                .video(video)
                .questionId(question.getId())
                .question(question.getContent())
                .build();
    }
}
