package inflearn.interview.workbook.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import inflearn.interview.common.service.GptCallCountService;
import inflearn.interview.common.service.GptService;
import inflearn.interview.question.controller.response.QuestionListResponse;
import inflearn.interview.question.domain.Question;
import inflearn.interview.question.infrastructure.QuestionEntity;
import inflearn.interview.user.domain.User;
import inflearn.interview.user.service.UserRepository;
import inflearn.interview.workbook.controller.response.WorkbookListResponse;
import inflearn.interview.workbook.domain.Workbook;
import inflearn.interview.workbook.infrastructure.WorkbookEntity;
import inflearn.interview.question.domain.QuestionCreate;
import inflearn.interview.workbook.domain.WorkbookCreate;
import inflearn.interview.common.exception.GptCallCountExceededException;
import inflearn.interview.common.exception.OptionalNotFoundException;
import inflearn.interview.question.service.QuestionRepository;

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

    private Workbook getById(Long id) {
        return workbookRepository.findById(id).orElseThrow(OptionalNotFoundException::new);
    }


    public List<WorkbookListResponse> getWorkbookList(Long userId) {
        List<WorkbookEntity> workbookList = workbookRepository.findAllByUserId(userId);
        return WorkbookListResponse.from(workbookList);
    }

    public void createWorkbook(WorkbookCreate workbookCreate) throws JsonProcessingException {
        User user = userRepository.findById(workbookCreate.getUserId()).orElseThrow(OptionalNotFoundException::new);
        if (workbookCreate.getJob().isEmpty()) {
            Workbook workbook = Workbook.from(user, workbookCreate);
            workbookRepository.save(workbook);
            return;
        }
        if (callCountService.getQuestionCount(user.getId()) < QUESTION_MAX_COUNT) {
            callCountService.plusQuestionCallCount(user.getId());
            Workbook workbook = Workbook.from(user, workbookCreate);
            workbook = workbookRepository.save(workbook);

            String[] questionAndAnswer = gptService.GPTWorkBook(workbookCreate.getJob());

            QuestionCreate questionCreate = QuestionCreate.builder()
                    .userId(user.getId())
                    .questionContent(questionAndAnswer[0])
                    .answerContent(questionAndAnswer[1])
                    .build();

            createQuestion(workbook.getId(), questionCreate);
        } else {
            throw new GptCallCountExceededException();
        }
    }


    public void deleteWorkbook(Long workbookId) {
        Workbook workbook = getById(workbookId);
        workbookRepository.delete(workbook);
    }

    /**
     *  질문 세트
     */
    public void createQuestion(Long workbookId, QuestionCreate questionCreate) {
        Workbook workbook = getById(workbookId);
        workbook = workbook.plusNumOfQuestion();
        workbook = workbookRepository.save(workbook);

        Question question = Question.from(workbook, questionCreate);
        questionRepository.save(question);
    }

    public void deleteQuestion(Long workbookId, Long questionId) {
        Workbook workbook = getById(workbookId);
        workbook = workbook.minusNumOfQuestion();
        workbookRepository.save(workbook);

        Question question = questionRepository.findById(questionId).orElseThrow(OptionalNotFoundException::new);
        questionRepository.delete(question);

    }

    public List<QuestionListResponse> findQuestionList(Long workbookId) {
        List<QuestionEntity> list = questionRepository.findAllByWorkbook(workbookId);
        return QuestionListResponse.from(list);
    }



}

