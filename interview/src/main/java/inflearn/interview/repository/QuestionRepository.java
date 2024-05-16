package inflearn.interview.repository;

import inflearn.interview.domain.Question;
import inflearn.interview.domain.Workbook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByWorkbook(Workbook workbook);
}
