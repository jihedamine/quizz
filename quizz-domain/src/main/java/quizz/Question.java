package quizz;

import quizz.exception.QuestionFormatException;
import quizz.exception.TokenFormatException;
import quizz.tokens.Delimiter;
import quizz.tokens.Token;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Question {

    private final String question;
    private final List<String> wrongAnswers;
    private final List<String> correctAnswers;
    private final String explanation;

    public Question(String question) {
        List<Token> tokens = getTokens(question);
        List<String> questionTokensValues = tokens.stream().filter(token -> Delimiter.QUESTION == token.getDelimiter())
                .map(Token::getValue).collect(Collectors.toList());
        List<String> wrongAnswerTokensValues = tokens.stream().filter(token -> Delimiter.WRONG_ANSWER == token.getDelimiter())
                .map(Token::getValue).collect(Collectors.toList());
        List<String> correctAnswerTokensValues = tokens.stream().filter(token -> Delimiter.CORRECT_ANSWER == token.getDelimiter())
                .map(Token::getValue).collect(Collectors.toList());
        List<String> explanationTokensValues = tokens.stream().filter(token -> Delimiter.EXPLANATION == token.getDelimiter())
                .map(Token::getValue).collect(Collectors.toList());

        if (questionTokensValues.size() != 1) {
            throw new QuestionFormatException("A question must have one and only one question token", questionTokensValues);
        }
        if (wrongAnswerTokensValues.size() < 1) {
            throw new QuestionFormatException("A question must have at least one wrong answer", wrongAnswerTokensValues);
        }
        if (correctAnswerTokensValues.size() < 1) {
            throw new QuestionFormatException("A question must have at least one correct answer", correctAnswerTokensValues);
        }
        if (explanationTokensValues.size() != 1) {
            throw new QuestionFormatException("A question must have one and only one explanation token", explanationTokensValues);
        }

        this.question = questionTokensValues.get(0);
        this.explanation = explanationTokensValues.get(0);
        this.wrongAnswers = wrongAnswerTokensValues;
        this.correctAnswers = correctAnswerTokensValues;
    }

    @Override
    public String toString() {
        return "Question:\n -Q: " + question + "\n -Wrong answers: " + wrongAnswers
                + "\n -Correct answers: " + correctAnswers + "\n -Explanation: " + explanation;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getWrongAnswers() {
        return new ArrayList<>(wrongAnswers);
    }

    public List<String> getCorrectAnswers() {
        return new ArrayList<>(correctAnswers);
    }

    public String getExplanation() {
        return explanation;
    }

    private static List<Token> getTokens(String question) {
        List<Token> tokens = new ArrayList<>();
        Queue<String> lines = new LinkedList<>();
        for (String line : question.split("\n")) {
            lines.add(line);
        }

        while (!lines.isEmpty()) {
            Optional<Delimiter> delim = getAnyMultiLineToken(lines.peek());
            if (delim.isPresent()) {
                tokens.add(getMultiLineToken(lines, delim.get()));
            } else {
                Optional<Delimiter> delim2 = getAnyOneLineToken(lines.peek());
                if (delim2.isPresent()) tokens.add(new Token(delim2.get(), lines.poll()));
            }

        }
        return tokens;
    }

    private static Optional<Delimiter> getAnyMultiLineToken(String line) {
        return Arrays.stream(Delimiter.values())
                .filter(isMultiLineToken(line)).findAny();
    }

    private static Optional<Delimiter> getAnyOneLineToken(String line) {
        return Arrays.stream(Delimiter.values()).filter(isOneLineToken(line)).findAny();
    }

    private static Predicate<Delimiter> isOneLineToken(String readLine) {
        return delimiter -> readLine != null && readLine.startsWith(delimiter.getDelimiter());
    }

    private static Predicate<Delimiter> isMultiLineToken(String readLine) {
        return delimiter -> readLine != null && readLine.startsWith(Delimiter.MULTILINE_DELIM + delimiter.getDelimiter());
    }

    private static Token getMultiLineToken(Queue<String> lines, Delimiter delimiter) {
        StringBuffer sb = new StringBuffer();
        String lastLine = "";
        while (!lines.isEmpty() &&
                !lastLine.endsWith(delimiter.getDelimiter() + Delimiter.MULTILINE_DELIM)) {
            lastLine = lines.poll();
            sb.append(lastLine);
            sb.append("\n");
        }

        if (!lastLine.endsWith(delimiter.getDelimiter() + Delimiter.MULTILINE_DELIM))
            throw new TokenFormatException("MultiLine token without proper ending delimiter: " + sb.toString());

        return new Token(delimiter, sb.toString());
    }

}
