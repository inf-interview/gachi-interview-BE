package inflearn.interview.controller;

import inflearn.interview.domain.DO.Video;
import inflearn.interview.domain.dto.VideoDTO;
import inflearn.interview.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
public class VideoController {
    /**
     * 게시글(영상) 조회
     * 게시글(영상) 수정
     * 영상 삭제
     * 게시글(영상) 좋아요
     * 영상 게시판 목록조회
     */

    private final VideoService videoService;

    @GetMapping("/{video_id}")
    public VideoDTO viewController(@PathVariable Long video_id){
        return videoService.getVideoById(video_id);
    }

    @PatchMapping("/{video_id}")
    public String editController(@PathVariable Long video_id, @RequestBody VideoDTO video){
        videoService.updateVideo(video_id, video);
        return "ok";
    }

    @DeleteMapping("/{video_id}")
    public String deleteController(@PathVariable Long video_id){
        videoService.deleteVideo(video_id);
        return null;
    }

    @PostMapping("/{video_id}/like")
    public String likeVideoController(@PathVariable String video_id){
        //db에 좋아요 추가
        return null;
    }

    @GetMapping("/list")
    public String videoListController(@RequestParam String page){
        //db에서 목록 조회에서 넘겨주기
        return null;
    }

}