package inflearn.interview.feedback.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import inflearn.interview.user.domain.User;
import inflearn.interview.feedback.domain.FeedbackDTO;
import inflearn.interview.common.domain.GptCountDTO;
import inflearn.interview.common.service.GptCallCountService;
import inflearn.interview.common.service.GptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final GptService gptService;
    private final GptCallCountService callCountService;

    @PostMapping("/{video_Id}")
    public void submit(@AuthenticationPrincipal User user, @PathVariable Long video_Id, @RequestBody FeedbackDTO dto) throws JsonProcessingException {
        gptService.GPTFeedback(video_Id, user, dto);
    }

    @GetMapping("/limits")
    public ResponseEntity<?> checkLimits(@AuthenticationPrincipal User user) {
        Integer count = callCountService.interviewCountToClient(user.getId());
        GptCountDTO dto = new GptCountDTO(3, count);
        return ResponseEntity.ok(dto);
    }

}