package inflearn.interview.workbook.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkbookJpaRepository extends JpaRepository<WorkbookEntity, Long> {

    List<WorkbookEntity> findAllByUserEntityId(Long userId);
}
