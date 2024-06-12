package inflearn.interview.service;

import inflearn.interview.domain.Question;
import inflearn.interview.domain.User;
import inflearn.interview.domain.Workbook;
import inflearn.interview.domain.dto.QuestionRequestDTO;
import inflearn.interview.domain.dto.WorkbookRequestDTO;
import inflearn.interview.exception.OptionalNotFoundException;
import inflearn.interview.repository.QuestionRepository;
import inflearn.interview.repository.UserRepository;
import inflearn.interview.repository.WorkbookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class WorkbookService {

    private final WorkbookRepository workbookRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    public List<Workbook> getWorkbooks(User user) {
        return workbookRepository.findAllByUser(user);
    }

    public void createWorkbook(WorkbookRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(OptionalNotFoundException::new);
        Workbook workbook = new Workbook(user, dto.getTitle());
        workbookRepository.save(workbook);
    }

    public Workbook findWorkbook(Long workbookId) {
        return workbookRepository.findById(workbookId).orElseGet(null);
    }


    public Workbook updateWorkbook(Long workbookId, String newTitle) {
        Workbook workbook = findWorkbook(workbookId);
        workbook.setTitle(newTitle);
        return workbookRepository.save(workbook);
    }

    public void deleteWorkbook(Long workbookId) {
        Workbook workbook = workbookRepository.findById(workbookId).orElseThrow(OptionalNotFoundException::new);
        workbookRepository.delete(workbook);
    }

    /**
     *  질문 세트
     */
    public void createQuestion(Long workbookId, QuestionRequestDTO dto) {
        Workbook workbook = workbookRepository.findById(workbookId).orElseThrow(OptionalNotFoundException::new);
        workbook.increaseNumOfQuestion();

        Question question = new Question(workbook, dto.getQuestionContent(), dto.getAnswerContent());
        questionRepository.save(question);
    }

    public void deleteQuestion(Long workbookId, Long questionId) {
        Workbook workbook = workbookRepository.findById(workbookId).orElseThrow(OptionalNotFoundException::new);
        workbook.decreaseNumOfQuestion();

        questionRepository.deleteById(questionId);
    }

    public List<Question> findQuestion(Long workbookId) {
        Workbook workbook = workbookRepository.findById(workbookId).orElseThrow(OptionalNotFoundException::new);
        return questionRepository.findByWorkbook(workbook);
    }
}

