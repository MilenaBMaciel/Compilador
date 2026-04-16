package lexer;

public class Word extends Token {

    public String lexeme = "";

    public Word(String s, int tag) {
        super(tag);
        lexeme = s;
    }

    @Override
    public String toString() {
        return lexeme;
    }

    public static final Word assign = new Word(":=", Tag.ASSIGN),
            le = new Word("<=", Tag.LE),
            ge = new Word(">=", Tag.GE),
            ne = new Word("<>", Tag.NE),
            and = new Word("and", Tag.AND),
            or = new Word("or", Tag.OR),
            not = new Word("not", Tag.NOT);
}
