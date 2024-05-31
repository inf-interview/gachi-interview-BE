package inflearn.interview.controller;


import inflearn.interview.domain.User;
import inflearn.interview.domain.dto.VideoDTO;
import inflearn.interview.domain.dto.VideoDTO2;
import inflearn.interview.service.VideoLikeService;
import inflearn.interview.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;
    private final VideoLikeService videoLikeService;

    @GetMapping("/{video_id}")
    public VideoDTO2 getVideoController(@PathVariable Long video_id){
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
    public Map<String, Long> likeVideoController(@PathVariable Long video_id, @RequestBody User user){
        return Map.of("numOfLike", videoLikeService.addLike(video_id, user));
    }

    /**
     * 페이징이랑 sortType맞춰서 수정
     */

    @GetMapping("/list")
    public Page<VideoDTO2> videoListController(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "time") String sortType) {
        return videoService.getVideoList(sortType, page);
    }
}