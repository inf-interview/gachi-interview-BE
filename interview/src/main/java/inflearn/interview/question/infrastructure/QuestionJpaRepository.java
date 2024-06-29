package inflearn.interview.question.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionJpaRepository extends JpaRepository<QuestionEntity, Long> {
    List<QuestionEntity> findAllByWorkbookEntityId(Long workbookId);
}
