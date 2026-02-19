void main() {

    var code = """
            import java.util.regex.Pattern;
            
            void main() {
            	String initial_variable = IO.readln("Input initial value: ");
            	String regex ="^[a-zA-Z_$][a-zA-Z\\\\d_$]*$";
            
            	boolean isValid = Pattern.matches(regex, initial_variable);
            
            	IO.println("Is the provided variable name valid ? : " + isValid);
            }
            """;

    IO.println("Start Time: " + System.currentTimeMillis());
    var tokens = tokenize(code);
    IO.println("End Time: " + System.currentTimeMillis());

    tokens.forEach(IO::println);
}

List<Token> tokenize(String input) {

    // We escape special regex chars like ( ) [ ] with \\
    final String KEYWORDS = "\\b(class|public|static|void|int|if|else|return)\\b";
    final String IDENTIFIERS = "\\b[a-zA-Z_][a-zA-Z0-9_]*\\b";
    final String OPERATORS = "[=+\\-*/<>!]";
    final String LITERALS = "\\b\\d+\\b";
    final String SEPARATORS = "[(){}\\[\\]\\,.;]"; // Added separators here

    var tokens = new ArrayList<Token>();

    // Compile patterns with named groups for easy identification
    var pattern = Pattern.compile(
            String.format("(?<KEYWORD>%s)|(?<IDENTIFIER>%s)|(?<OPERATOR>%s)|(?<LITERAL>%s)|(?<SEPARATOR>%s)",
                    KEYWORDS, IDENTIFIERS, OPERATORS, LITERALS, SEPARATORS)
    );

    var matcher = pattern.matcher(input);

    while (matcher.find()) {

//        if (matcher.group("KEYWORD") != null) {
//            tokens.add(new Token(TokenType.KEYWORD, matcher.group("KEYWORD")));
//        } else if (matcher.group("IDENTIFIER") != null) {
//            tokens.add(new Token(TokenType.IDENTIFIER, matcher.group("IDENTIFIER")));
//        } else if (matcher.group("OPERATOR") != null) {
//            tokens.add(new Token(TokenType.OPERATOR, matcher.group("OPERATOR")));
//        } else if (matcher.group("LITERAL") != null) {
//            tokens.add(new Token(TokenType.LITERAL, matcher.group("LITERAL")));
//        }

        String matchedKind =
                matcher.group("KEYWORD") != null ? "KEYWORD" :
                        matcher.group("IDENTIFIER") != null ? "IDENTIFIER" :
                                matcher.group("OPERATOR") != null ? "OPERATOR" :
                                        matcher.group("LITERAL") != null ? "LITERAL" :
                                                matcher.group("SEPARATOR") != null ? "SEPARATOR" :
                                                        "UNKNOWN";

        switch (matchedKind) {
            case "KEYWORD" -> tokens.add(new Token(TokenType.KEYWORD, matcher.group("KEYWORD")));
            case "IDENTIFIER" -> tokens.add(new Token(TokenType.IDENTIFIER, matcher.group("IDENTIFIER")));
            case "OPERATOR" -> tokens.add(new Token(TokenType.OPERATOR, matcher.group("OPERATOR")));
            case "LITERAL" -> tokens.add(new Token(TokenType.LITERAL, matcher.group("LITERAL")));
            case "SEPARATOR" -> tokens.add(new Token(TokenType.SEPARATOR, matcher.group("SEPARATOR")));
            default -> {
                // This should never happen due to the regex structure, but we can log it just in case
                System.err.println("Unknown token: " + matcher.group());
            }
        }

    }

    return tokens;
}

enum TokenType {
    KEYWORD, IDENTIFIER, OPERATOR, LITERAL, SEPARATOR, UNKNOWN
}

record Token(TokenType type, String value) {
    @Override
    public String toString() {
        return String.format("[%s: %s]", type, value);
    }
}
