package inflearn.interview.workbook.infrastructure;

import inflearn.interview.workbook.domain.Workbook;
import inflearn.interview.workbook.service.WorkbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WorkbookRepositoryImpl implements WorkbookRepository {

    private final WorkbookJpaRepository workbookJpaRepository;

    @Override
    public List<WorkbookEntity> findAllByUserId(Long userId) {
        return workbookJpaRepository.findAllByUserEntityId(userId);
    }

    @Override
    public Optional<Workbook> findById(Long id) {
        return workbookJpaRepository.findById(id).map(WorkbookEntity::toModel);
    }

    @Override
    public Workbook save(Workbook workbook) {
        return workbookJpaRepository.save(WorkbookEntity.fromModel(workbook)).toModel();
    }

    @Override
    public void delete(Workbook workbook) {
        workbookJpaRepository.delete(WorkbookEntity.fromModel(workbook));
    }
}
