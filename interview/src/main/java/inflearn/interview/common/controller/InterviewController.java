package inflearn.interview.common.controller;

import inflearn.interview.common.domain.UrlReturnDTO;
import inflearn.interview.video.domain.VideoCreate;
import inflearn.interview.common.domain.VideoNameDTO;
import inflearn.interview.common.service.S3Service;
import inflearn.interview.video.service.VideoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/interview")
@RequiredArgsConstructor
public class InterviewController {
    /**
     * 영상 녹화 완료
     */
    private final VideoServiceImpl videoServiceImpl;
    private final S3Service s3Service;

    @PostMapping("/complete")
    public ResponseEntity<?> complete(@RequestBody VideoCreate videoCreate){
        Long videoId = videoServiceImpl.create(videoCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("videoId", videoId));
    }

    @PostMapping("/presigned")
    public ResponseEntity<?> createPresigned(@RequestBody VideoNameDTO videoNameDTO) {
        UrlReturnDTO urlDTO = s3Service.getPresignedUrl(videoNameDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(urlDTO);
    }
}