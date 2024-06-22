package inflearn.interview.service;

import inflearn.interview.domain.User;
import inflearn.interview.domain.dto.GptCountDTO;
import inflearn.interview.exception.OptionalNotFoundException;
import inflearn.interview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class GptCallCountService {

    private static final Integer INTERVIEW_MAX_COUNT = 3;

    private final UserRepository userRepository;

    public boolean checkQuestionCallCount(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);
        //유저의 questionCall이 0인 경우
        //LocalDateTime을 지금 시간으로 초기화
        //count++;
        if (user.getQuestionGptCallCount() == null || user.getQuestionGptCallCount() == 0) {
            user.setQuestionGptCallTime(LocalDateTime.now());
            user.setQuestionGptCallCount(1);
            //유저의 questionCall이 1~4 인 경우
            //count만 1씩 계속 늘리기
        } else if (user.getQuestionGptCallCount() <= 4) {
            user.setQuestionGptCallCount(user.getQuestionGptCallCount() + 1);
            //유저의 questionCall이 5인경우
            //1. 5인 경우인데 LocalDateTime이 24시간이 안 지난 경우 -> 막고 예외 처리
            //2. 5인 경우인데 LocalDateTime이 24시간이 지난 경우 -> 새로 1로 할당하고 LocalDateTime 현재시간으로 초기화
        } else if (user.getQuestionGptCallCount() == 5) {
            if (user.getQuestionGptCallTime().isAfter(user.getQuestionGptCallTime().plusDays(1))) {
                user.setQuestionGptCallCount(1);
                user.setQuestionGptCallTime(LocalDateTime.now());
            } else {
                return false;
            }
        }
        return true;

    }

    public boolean checkInterviewCallCount(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);

        if (user.getInterviewGptCallCount() == null || user.getInterviewGptCallCount() == 0) {
            user.setInterviewGptCallTime(LocalDateTime.now());
            user.setInterviewGptCallCount(1);

        } else if (user.getInterviewGptCallCount() <= 2) {
            user.setInterviewGptCallCount(user.getInterviewGptCallCount() + 1);

        } else if (user.getInterviewGptCallCount() == 3) {
            if (user.getInterviewGptCallTime().isAfter(user.getInterviewGptCallTime().plusDays(1))) {
                user.setInterviewGptCallCount(1);
                user.setInterviewGptCallTime(LocalDateTime.now());
            } else {
                return false;
            }
        }
        return true;

    }

    @Transactional(readOnly = true)
    public GptCountDTO getInterviewCount(User user) {
        return new GptCountDTO(INTERVIEW_MAX_COUNT, user.getInterviewGptCallCount());
    }
}
