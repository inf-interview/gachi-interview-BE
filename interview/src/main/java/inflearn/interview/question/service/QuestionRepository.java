package inflearn.interview.question.service;

import inflearn.interview.question.domain.Question;
import inflearn.interview.workbook.domain.Workbook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByWorkbook(Workbook workbook);
}
