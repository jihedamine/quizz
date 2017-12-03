package quizz.tokens;

public enum Delimiter {
    QUESTION("?"), WRONG_ANSWER("-"), CORRECT_ANSWER("+"), EXPLANATION("#");
    public static final String MULTILINE_DELIM = "/";
    public static final String QUESTION_ENTRY_DELIM = "---";

    private String delimiter;

    private Delimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getDelimiter() {
        return delimiter;
    }
}
