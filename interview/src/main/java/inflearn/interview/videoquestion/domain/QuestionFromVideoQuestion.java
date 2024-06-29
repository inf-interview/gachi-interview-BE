package inflearn.interview.videoquestion.domain;

import inflearn.interview.videoquestion.infrastructure.VideoQuestionEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class QuestionFromVideoQuestion {
    private String question;

    @Builder
    public QuestionFromVideoQuestion(String question) {
        this.question = question;
    }

    public static List<QuestionFromVideoQuestion> from(List<VideoQuestionEntity> list) {
        return list.stream().map(videoQuestionEntity -> QuestionFromVideoQuestion.builder()
                .question(videoQuestionEntity.getQuestion()).build()).collect(Collectors.toList());
    }
}
