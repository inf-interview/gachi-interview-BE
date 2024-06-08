package inflearn.interview.controller;

import inflearn.interview.aop.ValidateUser;
import inflearn.interview.domain.User;
import inflearn.interview.domain.dto.QuestionRequestDTO;
import inflearn.interview.domain.dto.QuestionResponseDTO;
import inflearn.interview.domain.dto.WorkbookRequestDTO;
import inflearn.interview.domain.dto.WorkbookResponseDTO;
import inflearn.interview.service.WorkbookService;
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
    public ResponseEntity<List<WorkbookResponseDTO>> getWorkbookList(@AuthenticationPrincipal User user) {
        List<WorkbookResponseDTO> workbooks = workbookService.getWorkbooks(user).stream()
                .map(WorkbookResponseDTO::new).collect(Collectors.toList());

        return new ResponseEntity<>(workbooks, HttpStatus.OK);
    }

    @ValidateUser
    @PostMapping
    public ResponseEntity<String> createWorkbook(@RequestBody WorkbookRequestDTO dto) {
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




