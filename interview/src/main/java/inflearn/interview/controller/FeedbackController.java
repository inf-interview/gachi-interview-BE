package inflearn.interview.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import inflearn.interview.aop.ValidateUser;
import inflearn.interview.domain.Feedback;
import inflearn.interview.domain.User;
import inflearn.interview.domain.dto.FeedbackDTO;
import inflearn.interview.domain.dto.GptCountDTO;
import inflearn.interview.service.GptCallCountService;
import inflearn.interview.service.GptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final GptService gptService;
    private final GptCallCountService callCountService;

    @ValidateUser
    @PostMapping("/{video_Id}")
    public void submit(@AuthenticationPrincipal User user, @PathVariable Long video_Id, @RequestBody FeedbackDTO dto) throws JsonProcessingException {
        gptService.GPTFeedback(video_Id, user, dto);
    }

    @GetMapping("/limits")
    public ResponseEntity<?> checkLimits(@AuthenticationPrincipal User user) {
        Integer count = callCountService.interviewCountToClient(user.getUserId());
        GptCountDTO dto = new GptCountDTO(3, count);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{feedbackId}")
    public void delete(@PathVariable Long feedbackId) {
        gptService.deleteFeedback(feedbackId);
    }

    @GetMapping("/{userId}")
    public List<Feedback> getFeedbacks(@RequestParam Long userId){
        return gptService.getFeedbacks(userId);
    }

    @GetMapping("/{feedbackId}")
    public Feedback getFeedback(@PathVariable Long feedbackId){
        return gptService.getFeedback(feedbackId);
    }
}