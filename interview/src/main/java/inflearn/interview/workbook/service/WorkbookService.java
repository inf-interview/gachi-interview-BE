package inflearn.interview.workbook.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import inflearn.interview.common.service.GptCallCountService;
import inflearn.interview.common.service.GptService;
import inflearn.interview.question.domain.Question;
import inflearn.interview.user.domain.User;
import inflearn.interview.workbook.domain.Workbook;
import inflearn.interview.question.domain.QuestionRequestDTO;
import inflearn.interview.workbook.domain.WorkbookRequestDTO;
import inflearn.interview.common.exception.GptCallCountExceededException;
import inflearn.interview.common.exception.OptionalNotFoundException;
import inflearn.interview.question.service.QuestionRepository;
import inflearn.interview.user.service.UserRepository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static inflearn.interview.common.constant.GptCount.QUESTION_MAX_COUNT;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class WorkbookService {

    private final WorkbookRepository workbookRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final GptService gptService;
    private final GptCallCountService callCountService;

    public List<Workbook> getWorkbooks(User user) {
        return workbookRepository.findAllByUser(user);
    }

    public void createWorkbook(WorkbookRequestDTO dto) throws JsonProcessingException {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(OptionalNotFoundException::new);
        if (dto.getJob().isEmpty()) {
            Workbook workbook = new Workbook(user, dto.getTitle());
            workbookRepository.save(workbook);
            return;
        }
        if (callCountService.getQuestionCount(user.getUserId()) < QUESTION_MAX_COUNT) {
            callCountService.plusQuestionCallCount(user.getUserId());
            Workbook saved = workbookRepository.save(new Workbook(user, dto.getTitle()));
            String[] questionAndAnswer = gptService.GPTWorkBook(dto.getJob());
            QuestionRequestDTO questionDto = new QuestionRequestDTO(dto.getUserId(), questionAndAnswer[0], questionAndAnswer[1]);
            createQuestion(saved.getId(), questionDto);
        } else {
            throw new GptCallCountExceededException();
        }
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

