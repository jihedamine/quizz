package quizz.tokens;

public class Token {
    private final Delimiter delimiter;

    private final String value;

    public Token(Delimiter delimiter, String value) {
        this.delimiter = delimiter;
        if (value.startsWith(Delimiter.MULTILINE_DELIM)) {
            int offset = Delimiter.MULTILINE_DELIM.length() + delimiter.getDelimiter().length();
            this.value = value.substring(offset, value.length() - offset - 1).trim();
        } else {
            this.value = value.substring(delimiter.getDelimiter().length()).trim();
        }
    }

    public Delimiter getDelimiter() {
        return delimiter;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Token type = " + delimiter + ", value = " + value;
    }
}
