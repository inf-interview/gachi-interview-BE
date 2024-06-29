package inflearn.interview.question.domain;

import inflearn.interview.common.domain.BaseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateQuestion implements BaseDTO {

    private Long userId;
    private String questionContent;
    private String answerContent;

    public CreateQuestion() {
    }

    @Builder
    public CreateQuestion(Long userId, String questionContent, String answerContent) {
        this.userId = userId;
        this.questionContent = questionContent;
        this.answerContent = answerContent;
    }

}
