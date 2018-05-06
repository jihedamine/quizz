package quizz;

import quizz.tokens.Delimiter;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Files.newBufferedReader;

public class QuestionFileParser {

    public static List<Question> getQuestions(Path questionPath) {
        return getQuestionStrings(questionPath).stream().map(qs -> new Question(qs)).collect(Collectors.toList());
    }

    private static List<String> getQuestionStrings(Path questionPath) {
        List<String> questions = new ArrayList<>();
        try (BufferedReader reader = newBufferedReader(questionPath, StandardCharsets.UTF_8)) {
            String line = reader.readLine();
            while (line != null) {
                while (line != null && !line.startsWith(Delimiter.QUESTION_ENTRY_DELIM)) {
                    line = reader.readLine();
                }
                if (line != null && line.startsWith(Delimiter.QUESTION_ENTRY_DELIM)) {
                    StringBuilder sb = new StringBuilder();
                    line = reader.readLine();
                    while (line != null && !line.startsWith(Delimiter.QUESTION_ENTRY_DELIM)) {
                        sb.append(line);
                        sb.append("\n");
                        line = reader.readLine();
                    }
                    questions.add(sb.toString().trim());
                }
            }

            return questions;
        } catch (IOException e) {
            e.printStackTrace();
            return questions;
        }
    }
}
