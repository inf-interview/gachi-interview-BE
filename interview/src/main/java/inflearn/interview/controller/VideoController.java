package inflearn.interview.controller;


import inflearn.interview.domain.User;
import inflearn.interview.domain.dto.VideoDTO;
import inflearn.interview.service.VideoLikeService;
import inflearn.interview.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;
    private final VideoLikeService videoLikeService;

    @GetMapping("/{video_id}")
    public VideoDTO getVideoController(@PathVariable Long video_id){
        return videoService.getVideoById(video_id);
    }

    @PatchMapping("/{video_id}")
    public void editController(@PathVariable Long video_id, @RequestBody VideoDTO video){
        videoService.updateVideo(video_id, video);
    }

    @DeleteMapping("/{video_id}")
    public void deleteController(@PathVariable Long video_id){
        videoService.deleteVideo(video_id);
    }

    @PostMapping("/{video_id}/like")
    public void likeVideoController(@PathVariable Long video_id, @RequestBody User user){
        videoLikeService.addLike(video_id, user);
    }

    @DeleteMapping("/{video_id}/like")
    public void deleteVideoLike(@PathVariable Long video_id, @RequestBody User user){
        videoLikeService.deleteLike(video_id, user);
    }

    @GetMapping("/list")
    public Page<VideoDTO> videoListController(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return videoService.getVideoList(page, size);
    }
}