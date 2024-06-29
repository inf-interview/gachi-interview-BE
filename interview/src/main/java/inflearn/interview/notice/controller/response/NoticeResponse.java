package inflearn.interview.notice.controller.response;

import inflearn.interview.notice.infrastructure.Notice;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NoticeResponse {

    private Long id;

    private String content;

    private LocalDateTime createdAt;

    public NoticeResponse(Notice notice) {
        this.id = notice.getId();
        this.content = notice.getContent();
        this.createdAt = notice.getCreatedAt();
    }
}
