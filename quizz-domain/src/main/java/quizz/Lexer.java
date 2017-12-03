package quizz;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    public static enum TokenType {
        QUESTION("[?|?.*?]"), WRONG_ANSWER("-"), WHITESPACE("[ \t\f\r\n]+");

        public final String pattern;

        private TokenType(String pattern) {
            this.pattern = pattern;
        }
    }


    public static class Token {
        public TokenType type;
        public String data;

        public Token(TokenType type, String data) {
            this.type = type;
            this.data = data;
        }

        @Override
        public String toString() {
            return String.format("(%s %s)", type.name(), data);
        }
    }

    public static List<Token> lex(String input) {
        // The tokens to return
        List<Token> tokens = new ArrayList<>();

        // Lexer logic begins here
        StringBuffer tokenPatternsBuffer = new StringBuffer();
        for (TokenType tokenType : TokenType.values())
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));

        // Begin matching tokens
        Matcher matcher = tokenPatterns.matcher(input);
        while (matcher.find()) {
            for (TokenType tokenType : TokenType.values())
                if (matcher.group(TokenType.WHITESPACE.name()) != null) {
                    continue;
                } else if (matcher.group(tokenType.name()) != null) {
                    tokens.add(new Token(tokenType, matcher.group(tokenType.name())));
                    continue;
                }
        }

        return tokens;
    }

    public static void main(String[] args) {
        String input = "11 + 22 - 33";

        // Create tokens and print them
        List<Token> tokens = lex(input);
        for (Token token : tokens)
            System.out.println(token);
    }
}
