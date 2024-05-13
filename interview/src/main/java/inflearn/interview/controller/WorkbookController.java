package inflearn.interview.controller;

import inflearn.interview.domain.User;
import inflearn.interview.domain.Workbook;
import inflearn.interview.dto.WorkbookRequestDto;
import inflearn.interview.dto.WorkbookResponseDto;
import inflearn.interview.service.WorkbookService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // TODO: 로컬 테스트용. 이후 삭제 필요
@RequestMapping("/interview/question")
@Controller
public class WorkbookController {

    private final WorkbookService workbookService;

    @GetMapping("/list")
    public ResponseEntity<List<WorkbookResponseDto>> getWorkbookList() {
        List<WorkbookResponseDto> workbooks = workbookService.getWorkbooks().stream()
                .map(WorkbookResponseDto::new).collect(Collectors.toList());

        return new ResponseEntity<>(workbooks, HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<WorkbookResponseDto> createWorkbook(User user,
            @RequestBody WorkbookRequestDto requestDto) {
        Workbook workbook = workbookService.createWorkbook(null, requestDto.getTitle());

        return new ResponseEntity<>(new WorkbookResponseDto(workbook), HttpStatus.OK);
    }

    @GetMapping("/list/{workbookId}")
    public ResponseEntity<WorkbookResponseDto> getWorkbook(@PathVariable Long workbookId) {
        Workbook workbook = workbookService.findWorkbook(workbookId);

        return new ResponseEntity<>(new WorkbookResponseDto(workbook), HttpStatus.OK);
    }

    @PatchMapping("/list/{workbookId}")
    public ResponseEntity<WorkbookResponseDto> updateWorkbook(@PathVariable Long workbookId,
            @RequestBody WorkbookRequestDto requestDto) {
        Workbook workbook = workbookService.updateWorkbook(workbookId, requestDto.getTitle());

        return new ResponseEntity<>(new WorkbookResponseDto(workbook), HttpStatus.OK);
    }

    @DeleteMapping("/list/{workbookId}")
    public ResponseEntity<?> deleteWorkbook(@PathVariable Long workbookId) {
        workbookService.deleteWorkbook(workbookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}




