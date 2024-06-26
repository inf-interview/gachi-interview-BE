package inflearn.interview.workbook.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import inflearn.interview.common.aop.ValidateUser;
import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.question.domain.QuestionRequestDTO;
import inflearn.interview.question.domain.QuestionResponseDTO;
import inflearn.interview.workbook.domain.WorkbookRequestDTO;
import inflearn.interview.workbook.domain.WorkbookResponseDTO;
import inflearn.interview.workbook.service.WorkbookService;
import java.util.List;
import java.util.stream.Collectors;
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
    public ResponseEntity<List<WorkbookResponseDTO>> getWorkbookList(@AuthenticationPrincipal UserEntity userEntity) {
        List<WorkbookResponseDTO> workbooks = workbookService.getWorkbooks(userEntity).stream()
                .map(WorkbookResponseDTO::new).collect(Collectors.toList());

        return new ResponseEntity<>(workbooks, HttpStatus.OK);
    }

    @ValidateUser
    @PostMapping
    public ResponseEntity<String> createWorkbook(@RequestBody WorkbookRequestDTO dto) throws JsonProcessingException {
        workbookService.createWorkbook(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ValidateUser
    @DeleteMapping("/{workbook_id}")
    public ResponseEntity<?> deleteWorkbook(@RequestBody WorkbookRequestDTO dto, @PathVariable(name = "workbook_id") Long workbookId) {
        workbookService.deleteWorkbook(workbookId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 질문 세트
     */
    @GetMapping("/{workbook_id}/question/list")
    public ResponseEntity<List<QuestionResponseDTO>> getWorkbook(@PathVariable(name = "workbook_id") Long workbookId) {
        List<QuestionResponseDTO> questions = workbookService.findQuestion(workbookId).stream().map(QuestionResponseDTO::new).toList();
        return ResponseEntity.ok(questions);

    }

    @ValidateUser
    @PostMapping("/{workbook_id}/question")
    public ResponseEntity<?> writeQuestion(@PathVariable(name = "workbook_id") Long workbookId, @RequestBody QuestionRequestDTO dto) {
        workbookService.createQuestion(workbookId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{workbook_id}/question/{question_id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable(name = "workbook_id") Long workbookId, @PathVariable(name = "question_id") Long questionId) {
        workbookService.deleteQuestion(workbookId, questionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}




