package inflearn.interview.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequestDTO implements BaseDTO{

    private Long userId;
    private String questionContent;
    private String answerContent;
}
