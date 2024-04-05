package inflearn.interview.controller;

import inflearn.interview.domain.dao.VideoCommentDAO;
import inflearn.interview.domain.dto.VideoCommentDTO;
import inflearn.interview.service.VideoCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/video/{video_id}")
@RequiredArgsConstructor
public class VideoCommentController {
    /**
     * 영상 목록 댓글 조회
     * 영상 댓글 조회
     * 영상 댓글 작성
     * 댓글 수정
     * 댓글 삭제
     */

    private final VideoCommentService commentService;

    @GetMapping
    public List<VideoCommentDAO> getVideoComments(@PathVariable("video_id") Long videoId) {
        return commentService.getComments(videoId);
    }

    @GetMapping("/{comment_id}")
    public VideoCommentDAO getVideoComment(@PathVariable("comment_id") Long commentId) {
        return commentService.getComment(commentId);
    }

    @PostMapping("/submit")
    public VideoCommentDAO addVideoComment(@PathVariable("video_id") Long videoId, @RequestBody VideoCommentDTO videoCommentDTO) {
        return commentService.addComment(videoId, videoCommentDTO);
    }

    @PatchMapping("/comments/{comment_id}")
    public void updateVideoComment(@PathVariable("comment_id") Long commentId, @RequestBody VideoCommentDTO videoCommentDTO) {
        commentService.editComment(commentId, videoCommentDTO);
    }

    @DeleteMapping("/comments/{comment_id}")
    public void deleteVideoComment(@PathVariable("comment_id") Long commentId, @RequestBody VideoCommentDTO videoCommentDTO) {
        commentService.deleteComment(commentId, videoCommentDTO);
    }


}
