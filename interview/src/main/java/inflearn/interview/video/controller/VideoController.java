package inflearn.interview.video.controller;

import inflearn.interview.user.domain.User;
import inflearn.interview.common.domain.ErrorResponse;
import inflearn.interview.video.controller.response.VideoDetailResponse;
import inflearn.interview.video.domain.VideoDTO2;
import inflearn.interview.common.exception.RequestDeniedException;
import inflearn.interview.video.domain.VideoDelete;
import inflearn.interview.video.domain.VideoUpdate;
import inflearn.interview.video.service.VideoService;
import inflearn.interview.videolike.controller.response.LikeResponse;
import inflearn.interview.videolike.domain.VideoLikeRequest;
import inflearn.interview.videolike.service.VideoLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;
    private final VideoLikeService videoLikeService;

    @GetMapping("/{video_id}")
    public ResponseEntity<?> getVideoController(@PathVariable Long video_id, @AuthenticationPrincipal User user) {
        try {
            VideoDetailResponse response = videoService.getVideoById(video_id, user);
            return ResponseEntity.ok(response);
        } catch (RequestDeniedException e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Access Denied", "권한이 없습니다");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @PatchMapping("/{video_id}")
    public ResponseEntity<?> editController(@PathVariable Long video_id, @RequestBody VideoUpdate videoUpdate) {
        videoService.update(videoUpdate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{video_id}")
    public void deleteController(@PathVariable Long video_id, @RequestBody VideoDelete videoDelete) {
        videoService.delete(videoDelete);
    }

    @PostMapping("/{video_id}/like")
    public ResponseEntity<LikeResponse> likeVideoController(@PathVariable Long video_id, @RequestBody VideoLikeRequest videoLikeRequest) {
        LikeResponse response = videoLikeService.addLike(videoLikeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/list")
    public Page<VideoDTO2> videoListController(@RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "new") String sortType,
                                               @RequestParam(defaultValue = "") String keyword) {
        return videoService.getVideoList(sortType, keyword, page);
    }

}