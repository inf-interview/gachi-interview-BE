package inflearn.interview.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import inflearn.interview.domain.Workbook;
import inflearn.interview.repository.WorkbookRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class WorkbookServiceTest {

    @Autowired
    WorkbookService workbookService;
    @Autowired
    WorkbookRepository workbookRepository;


    @Test
    void 워크북을_생성할_수_있다() {
        String title = "workbook1";
        // TODO : 유저 기능 합한 후 진짜 유저 넣어서 테스트 필요
        Workbook workbook = workbookService.createWorkbook(null, title);
        Workbook result = workbookRepository.findByTitle(title).orElseGet(null);
        assertThat(result).isEqualTo(workbook);
    }

    @Test
    void 유저_없이_워크북_생성하면_예외가_발생한다() {
        // TODO
    }


    @Test
    void 워크북_전체_조회가_가능하다() {
        workbookService.createWorkbook(null, "workbook1");
        workbookService.createWorkbook(null, "workbook2");
        List<Workbook> result = workbookRepository.findAll();
        assertThat(result).hasSize(2);
    }

    @Test
    void 워크북_없을_때는_전체_조회_결과값_0이다() {
        List<Workbook> result = workbookRepository.findAll();
        assertThat(result.size()).isZero();
    }

    @Test
    void id로_워크북을_찾을_수_있다() {
        Workbook workbook = workbookService.createWorkbook(null, "workbook1");
        Workbook result = workbookService.findWorkbook(workbook.getId());
        assertThat(result).isEqualTo(workbook);
    }

    @Test
    void 해당하는_워크북이_없으면_NPE가_발생한다() {
        // TODO : 예외 처리 필요
        Workbook workbook = workbookService.createWorkbook(null, "workbook1");
        assertThrows(NullPointerException.class, () -> workbookService.findWorkbook(123456789L));
    }

    @Test
    void 워크북_제목을_수정할_수_있다() {
        Workbook workbook = workbookService.createWorkbook(null, "workbook1");
        Workbook result = workbookService.updateWorkbook(workbook, "updated!");
        Workbook result2 = workbookRepository.findById(workbook.getId()).get();
        assertThat(result2.getTitle()).isEqualTo("updated!");
    }

    @Test
    void 워크북을_삭제할_수_있다() {
        Workbook workbook = workbookService.createWorkbook(null, "workbook1");
        workbookService.deleteWorkbook(workbook.getId());
        assertThrows(NullPointerException.class,
                () -> workbookService.findWorkbook(workbook.getId()));
    }

    @Test
    void 없는_워크북_삭제_시도는_처리된다() {
        // TODO
    }

    @Test
    void 작성자만_워크북을_삭제할_수_있다() {
        // TODO
    }
}