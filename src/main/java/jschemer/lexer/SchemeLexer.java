package jschemer.lexer;


public class SchemeLexer extends Lexer {
    public static final int SYMBOL = 2;
    public static final int LBRACK = 3;
    public static final int RBRACK = 4;
    public static final int NUMBER = 5;
    public static final String[] tokenNames = { "n/a", "<EOF>", "SYMBOL",
            "LBRACK", "RBRACK", "NUMBER" };

    public SchemeLexer(String input) {
        super(input);
    }

    @Override
    public Token nextToken() {
        while (c != EOF) {
            switch (c) {
            case ' ':
            case '\t':
            case '\n':
            case '\r':
                WS();
                continue;
            case '(':
                consume();
                return new Token(LBRACK, "(");
            case ')':
                consume();
                return new Token(RBRACK, ")");
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return NUMBER();
            default:
                return SYMBOL();
            }
        }
        return new Token(EOF_TYPE, "<EOF>");
    }

    boolean isValidSymbolChar() {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '?'
                || c == '_' || c == '*' || c == '+' || c == '/' || c == '%'
                || c == '!';
    }

    private Token NUMBER() {
        StringBuilder builder = new StringBuilder();
        do {
            builder.append(c);
            consume();
        } while (isValidNumberChar());
        return new Token(NUMBER, builder.toString());
    }

    private boolean isValidNumberChar() {
        return c >= '0' && c <= '9' || c == '.';
    }

    private Token SYMBOL() {
        StringBuilder builder = new StringBuilder();
        do {
            builder.append(c);
            consume();
        } while (isValidSymbolChar());
        return new Token(SYMBOL, builder.toString());
    }

    private void WS() {
        while (c == ' ' || c == '\t' || c == '\n' || c == '\r')
            consume();
    }

    @Override
    public String getTokenName(int tokenType) {
        return tokenNames[tokenType];
    }

}
