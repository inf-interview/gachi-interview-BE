package inflearn.interview.controller;

import inflearn.interview.aop.ValidateUser;
import inflearn.interview.domain.dto.PostCommentDTO;
import inflearn.interview.domain.dto.VideoCommentDTO;
import inflearn.interview.service.VideoCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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

    @GetMapping("/comments")
    public List<PostCommentDTO> getVideoCommentsController(@PathVariable("video_id") Long videoId) {
        return commentService.getComments(videoId);
    }

    @GetMapping("/{comment_id}")
    public VideoCommentDTO getVideoCommentController(@PathVariable("comment_id") Long commentId) {
        return commentService.getComment(commentId);
    }

    @ValidateUser
    @PostMapping("/submit")
    public void addVideoCommentController(@PathVariable("video_id") Long videoId, @RequestBody @Validated(VideoCommentDTO.create.class) VideoCommentDTO videoCommentDTO) {
        commentService.addComment(videoId, videoCommentDTO);
    }

    @ValidateUser
    @PatchMapping("/comments/{comment_id}")
    public void updateVideoCommentController(@PathVariable("comment_id") Long commentId, @RequestBody @Validated(VideoCommentDTO.patch.class) VideoCommentDTO videoCommentDTO) {
        commentService.editComment(commentId, videoCommentDTO);
    }

    @ValidateUser
    @DeleteMapping("/comments/{comment_id}")
    public void deleteVideoCommentController(@PathVariable("comment_id") Long commentId, @RequestBody @Validated(VideoCommentDTO.delete.class) VideoCommentDTO videoCommentDTO) {
        commentService.deleteComment(commentId, videoCommentDTO);
    }

}