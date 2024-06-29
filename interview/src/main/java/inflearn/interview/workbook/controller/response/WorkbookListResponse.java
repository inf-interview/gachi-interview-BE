package inflearn.interview.workbook.controller.response;

import inflearn.interview.workbook.infrastructure.WorkbookEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WorkbookListResponse {
    Long listId;
    String title;

    @Builder
    public WorkbookListResponse(Long listId, String title) {
        this.listId = listId;
        this.title = title;
    }

    public static List<WorkbookListResponse> from(List<WorkbookEntity> list) {
        return list.stream().map(workbookEntity -> WorkbookListResponse.builder()
                .listId(workbookEntity.getId())
                .title(workbookEntity.getTitle()).build()).collect(Collectors.toList());
    }

}
