package inflearn.interview.question.service;

import inflearn.interview.question.domain.Question;
import inflearn.interview.question.infrastructure.QuestionEntity;
import inflearn.interview.workbook.infrastructure.WorkbookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {

    Question save(Question question);

    Optional<Question> findById(Long questionId);

    void delete(Question question);

    List<QuestionEntity> findAllByWorkbook(Long workbookId);
}
