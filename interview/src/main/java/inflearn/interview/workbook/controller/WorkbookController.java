package inflearn.interview.workbook.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import inflearn.interview.user.domain.User;
import inflearn.interview.question.domain.QuestionCreate;
import inflearn.interview.question.controller.response.QuestionListResponse;
import inflearn.interview.workbook.domain.WorkbookCreate;
import inflearn.interview.workbook.controller.response.WorkbookListResponse;
import inflearn.interview.workbook.service.WorkbookService;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/workbook")
@RestController
public class WorkbookController {

    private final WorkbookService workbookService;

    @GetMapping("/list")
    public ResponseEntity<List<WorkbookListResponse>> getWorkbookList(@AuthenticationPrincipal User user) {
        List<WorkbookListResponse> list = workbookService.getWorkbookList(user.getId());
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<String> createWorkbook(@RequestBody WorkbookCreate workbookCreate) throws JsonProcessingException {
        workbookService.createWorkbook(workbookCreate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{workbook_id}")
    public ResponseEntity<?> deleteWorkbook(@RequestBody WorkbookCreate dto, @PathVariable(name = "workbook_id") Long workbookId) {
        workbookService.deleteWorkbook(workbookId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 질문 세트
     */
    @GetMapping("/{workbook_id}/question/list")
    public ResponseEntity<List<QuestionListResponse>> getWorkbook(@PathVariable(name = "workbook_id") Long workbookId) {
        List<QuestionListResponse> list = workbookService.findQuestionList(workbookId);
        return ResponseEntity.ok(list);

    }

    @PostMapping("/{workbook_id}/question")
    public ResponseEntity<?> writeQuestion(@PathVariable(name = "workbook_id") Long workbookId, @RequestBody QuestionCreate questionCreate) {
        workbookService.createQuestion(workbookId, questionCreate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{workbook_id}/question/{question_id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable(name = "workbook_id") Long workbookId, @PathVariable(name = "question_id") Long questionId) {
        workbookService.deleteQuestion(workbookId, questionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}




