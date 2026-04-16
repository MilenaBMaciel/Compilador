package lexer;

public class Token {

    public final int tag;

    public Token(int t) {
        tag = t;
    }

    @Override
    public String toString() {
        if (tag < 256) {
            return "" + (char) tag;
        }
        return "" + tag;
    }
}
