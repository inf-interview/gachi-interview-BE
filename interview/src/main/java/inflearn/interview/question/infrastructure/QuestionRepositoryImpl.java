package inflearn.interview.question.infrastructure;

import inflearn.interview.question.domain.Question;
import inflearn.interview.question.service.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository {

    private final QuestionJpaRepository questionJpaRepository;

    @Override
    public Question save(Question question) {
        return questionJpaRepository.save(QuestionEntity.fromModel(question)).toModel();
    }

    @Override
    public Optional<Question> findById(Long questionId) {
        return questionJpaRepository.findById(questionId).map(QuestionEntity::toModel);
    }

    @Override
    public void delete(Question question) {
        questionJpaRepository.delete(QuestionEntity.fromModel(question));
    }

    @Override
    public List<QuestionEntity> findAllByWorkbook(Long workbookId) {
        return questionJpaRepository.findAllByWorkbookEntityId(workbookId);
    }
}
