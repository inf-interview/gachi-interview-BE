package inflearn.interview.domain.dto;

import inflearn.interview.domain.Notice;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NoticeDTO {

    private Long id;

    private String content;

    private LocalDateTime createdAt;

    public NoticeDTO(Notice notice) {
        this.id = notice.getId();
        this.content = notice.getContent();
        this.createdAt = notice.getCreatedAt();
    }
}
