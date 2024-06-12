package inflearn.interview.exception;

import lombok.Getter;

@Getter
public class UserValidationException extends RuntimeException{

    private String path;
    public UserValidationException(String message, String path) {
        super(message);
        this.path = path;
    }


}
