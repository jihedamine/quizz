package quizz.exception;

import java.util.List;

public class QuestionFormatException extends RuntimeException {
    public QuestionFormatException(String s, List<String> values) {
        super(s + ": " + values);
    }
}
