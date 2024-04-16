package inflearn.interview.service;

import inflearn.interview.domain.Users;
import inflearn.interview.domain.Workbook;
import inflearn.interview.dto.WorkbookDto;
import inflearn.interview.repository.WorkbookRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkbookService {

    private final WorkbookRepository workbookRepository;

    public List<WorkbookDto> getWorkbooks() {
        return workbookRepository.findAll().stream().map(WorkbookDto::new)
                .collect(Collectors.toList());
    }

    public Workbook createWorkbook(Users user, String title) {
        // TODO : 반환값 재확인 필요
        Workbook workbook = new Workbook();
        workbook.setUser(user);
        workbook.setTitle(title);
        return workbookRepository.save(workbook);
    }

    public Workbook findWorkbook(Long workbookId) {
        return workbookRepository.findById(workbookId).orElseGet(null);
    }


    public Workbook updateWorkbook(Workbook workbook, String newTitle) {
        workbook.setTitle(newTitle);
        return workbookRepository.save(workbook);
    }

    public void deleteWorkbook(Long workbookId) {
        Workbook workbook = workbookRepository.findById(workbookId).get();
        workbookRepository.delete(workbook);
    }
}

