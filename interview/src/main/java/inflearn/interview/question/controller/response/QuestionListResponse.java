package inflearn.interview.question.controller.response;

import inflearn.interview.question.infrastructure.QuestionEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class QuestionListResponse {

    private Long questionId;
    private String questionContent;
    private String answerContent;

    @Builder
    public QuestionListResponse(Long questionId, String questionContent, String answerContent) {
        this.questionId = questionId;
        this.questionContent = questionContent;
        this.answerContent = answerContent;
    }

    public static List<QuestionListResponse> from(List<QuestionEntity> list) {
        return list.stream().map(questionEntity -> QuestionListResponse.builder()
                .questionId(questionEntity.getId())
                .questionContent(questionEntity.getContent())
                .answerContent(questionEntity.getAnswer()).build()).collect(Collectors.toList());
    }
}
